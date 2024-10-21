import sqlite3
# g for database interaction with sqlite
from flask import Flask, render_template, request, g, redirect, url_for, session

app = Flask(__name__)
app.secret_key = "its a secret key" 

#
DATABASE = 'assignment3.db'
# database helper function
# Taken from here : https://flask.palletsprojects.com/en/1.1.x/patterns/sqlite3/
def get_db():               #get attributes from g, open and connect data base, return a db connection
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

def make_dicts(cursor, row):
    return dict((cursor.description[idx][0], value)
                for idx, value in enumerate(row))

def query_db(query, args=(), one=False): #run a query
    cur = get_db().execute(query, args)
    rv = cur.fetchall()     # get a list of tuple(rv)
    cur.close()
    return (rv[0] if rv else None) if one else rv


#browse

@app.teardown_appcontext    # close database if server is down
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

##

## Shared page
# Home page
@app.route('/')
def home():
	# Use session log_in determine log in or not
	# Note: this should be add to every function exclude login,signup
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')
	##
	# 
	else:
		user_name = session['username']
		
		db = get_db()
		db.row_factory = make_dicts # here get me a list of dictionary
		user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
		db.close()
		# for index.html to show different version for current user.
		user_type = user_inf['usertype'] # instructor or student
		greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]

		# this will look for a folder called 'templates' in the same dictionary as'app.py'
		return render_template('index.html', parameters = [user_type, greeting_name])

# Login page
@app.route('/login', methods=['GET','POST'])
def login():
	
	db = get_db()
	db.row_factory = make_dicts
	users = []
	# Append all users to users list
	for user in query_db('select * from Users'):
		users.append(user)
	db.close()

	# GET
	if request.method == 'GET':
		# Check if error happen
		if 'errormsg' in session:
			errormsg = session['errormsg']
			saveusername = session['saveusername']
		else:
			# No error
			errormsg = ""
			saveusername = ""

		# If there is error
		if errormsg !="":
			# Pop out 'errormsg' and 'saveusername' session
			session.pop('errormsg')
			session.pop('saveusername')
		return render_template('login.html', emsg = errormsg, saveusername=saveusername)
	
	if request.method == 'POST':
		user_name = request.form['username']
		pwd = request.form['password']
		for user in users:
			# Check if the account exist
			# If not raise error message and clean all input boxes
			if user_name == user['username']:
				# Check if password correct
				# If not raise error message and clean only password box
				if pwd == user['password']:
					session['log_in'] = True
					session['username'] = user_name
					session['usertype'] = user['usertype']

					if 'errormsg' in session:
						session.pop('errormsg')
					return redirect('/')
				
				else:
					session['saveusername'] = user_name
					session['errormsg'] = "Error: The password does not match the username, please try again."
					break
			if 'errormsg' not in session:
				session['saveusername'] = ""
				session['errormsg'] = "Error: We couldn't find your account."
				
		return redirect('/login')

# Signup page
@app.route('/signup', methods=['GET', 'POST'])
def signup():
	db = get_db()
	db.row_factory = make_dicts
	users = []
	# default value for form
	saved_data = ["","","",1]
	# Append user to users list
	for user in query_db('select * from Users'):
		users.append(user)
	
	# GET
	if request.method == 'GET':
		# Check if error happen
		if 'errormsg' in session:
			errormsg = session['errormsg']
			saved_data[0] = session['saveusername']
			saved_data[1] = session['firstname']
			saved_data[2] = session['lastname']
			saved_data[3] = session['saveusertype']
		else:
			# No error
			errormsg = ""

		# If there is error
		if errormsg !="":
			session.pop('errormsg')
			session.pop('saveusername')
			session.pop('firstname')
			session.pop('lastname')
			session.pop('saveusertype')
		
		return render_template('signup.html', emsg = errormsg, saved_data = saved_data)
	
	# POST
	if request.method == 'POST':
		# request form 
		data = request.form
		user_name = data.get("username")
		first_name = data.get("firstname")
		last_name = data.get("lastname")
		firs_pwd = data.get("password")
		conf_pwd = data.get("conf_pwd")
		# set student as 0, instructor as 1
		type = 0 if data.get("usertype") == "student" else 1
		user_type = data.get("usertype")

		# Username should be unique, password should be same
		# Every fields are required

		# If any field is not filled, clean password, conf_pwd
		if not user_name or not first_name or not last_name or not firs_pwd or not conf_pwd:
			session['saveusername'] = user_name
			session['firstname'] = first_name
			session['lastname'] = last_name
			session['saveusertype'] = type
			session['errormsg'] = "Error: You must fill out all the fields"
		# If password and conf_pwd don't match, clean password, conf_pwd
		if firs_pwd != conf_pwd:
			session['saveusername'] = user_name
			session['firstname'] = first_name
			session['lastname'] = last_name
			session['saveusertype'] = type
			session['errormsg'] = "Error: The confirm password is not the same as the password you entered"
		# If username is not unique, clean username, password, conf_pwd
		for user in users:
			if user_name == user['username']:
				session['saveusername'] = ""
				session['firstname'] = first_name
				session['lastname'] = last_name
				session['saveusertype'] = type
				session['errormsg'] = "Error: The username has been used, please try a different username."
				break

		if 'errormsg' not in session:
			db.row_factory = make_dicts
			user_inf = "'{}', '{}', '{}', '{}', '{}'".format(
				user_name, first_name, last_name, firs_pwd, user_type)
			query_db("insert into Users (username, firstname, lastname, password, usertype) values (" + user_inf + ");")
			db.commit()
			# for student need insert grade table as well
			if type == 0:

				## Need to change while conform the keys for Grades table

				sql_insert = "insert into Grades (username, A1, A2, A3, A4, T1, T2, T3, finalexam) values ('"+ user_name + "', 0, 0, 0, 0, 0, 0, 0, 0);"
				query_db(sql_insert)
				db.commit()
			
			db.close()
			return redirect('/login')
		else:
			db.close()
			return redirect('/signup')
			

# Logout
@app.route('/logout')
def logout():
	# If user loged in, log out
	if 'log_in' in session:
		session.pop('log_in')
	return redirect('/login')


## Only accessible by student pages

# Feedback page
@app.route('/feedback', methods=['POST','GET'])
def feedback():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')
	# Block instructor from this page
	if session['usertype'] == "instructor":
		return redirect('/')

	db = get_db()
	db.row_factory = make_dicts
	users = []
	instructor_list = []
	# Apend user to users list, instructors to instructor_list
	for user in query_db('select * from Users'):
		users.append(user)
		if user['usertype'] == "instructor":
			instructor_list.append(user['firstname']+" "+user['lastname'])

	# Get
	if request.method == 'GET':
		# Check if error happen
		errormsg = session['errormsg'] if 'errormsg' in session else ""
		if errormsg !="":
			session.pop('errormsg')
		
		return render_template('feedback.html', emsg = errormsg, instructor_list = instructor_list)
	
	# POST
	if request.method == 'POST':
		# request form
		Answer1 = request.form.get("A1")
		Answer2 = request.form.get("A2")
		Answer3 = request.form.get("A3")
		Answer4 = request.form.get("A4")
		
		## instructor name
		full_name = request.form.get("instructor_name")

		# find out the ccorresponding username for this instructor
		for user in users:
			if full_name == (user['firstname'] + " " + user['lastname']):
				if user['usertype'] == 'instructor':
					user_name = user['username']
					break
				
		
		# Every fields are required
		if not Answer1 or not Answer2 or not Answer3 or not Answer4 or not full_name:
			session['errormsg'] = "Error: You must fill out all the fields"
		

		if 'errormsg' not in session:
			# No error, insert data to database
			db.row_factory = make_dicts
			feedback_detail = "'{}', '{}', '{}', '{}', '{}'".format(
				user_name, Answer1, Answer2, Answer3, Answer4)
			query_db("insert into Feedbacks (username, Answer1, Answer2, Answer3, Answer4) values (" + feedback_detail + ");")
			db.commit()
			db.close()
			
			# Sucessfully Submit, redirect to feedback page
			errormsg = "You have successfully submit your remark request."
			return render_template('feedback.html', emsg = errormsg, instructor_list = instructor_list)
		else:
			return redirect('/feedback')

# Grade page
@app.route('/grade')
def grade():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')
	user_name = session['username']
	# Query from database
	db = get_db()
	db.row_factory = make_dicts
	user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
	grade_detail = query_db('select * from Grades where username = ?', [user_name], one = True)
	request_status = query_db('select * from Request where username = ?', [user_name], one = False)

	db.close()

	user_type = user_inf['usertype']
	# Block instructor from this page
	if user_type == "instructor":
		return redirect('/instructorgrade')

	greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]
	grade_detail.pop('username')#pop username key so taht only left the grade
	for reque_stat in request_status:
		reque_stat.pop('username')
		reque_stat.pop('reason')
		reque_stat.pop('fullname')
	
	
	return render_template('grade.html', parameters=[user_type, greeting_name, grade_detail, request_status])

# Requestremark page
@app.route('/requestremark', methods=['POST','GET'])
def requestremark():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')
	# Block instructor from this page
	if session['usertype'] == "instructor":
		return redirect('/')
	
	db = get_db()
	db.row_factory = make_dicts
	users = []
	for user in query_db('select * from Users'):
		users.append(user)
	
	user_name = session['username']
	# Query from database
	grade_detail = query_db('select * from Grades where username = ?', [user_name], one = True)
	grade_detail.pop('username')
	# GET
	if request.method == 'GET':
		errormsg = session['errormsg'] if 'errormsg' in session else ""
		if errormsg !="":
			# No error
			session.pop('errormsg')

		return render_template('request.html', emsg = errormsg, grade_detail = grade_detail)
	
	# POST
	if request.method == 'POST':
		# request form
		project_id = request.form.get("Project_id")
		reason = request.form.get("explanation")
		user_name = session['username']

		# find out the full name for the student
		for user in users:
			if user_name == user['username']:
				full_name = user['firstname'][0].upper(
		) + user['firstname'][1:] + ' ' + user['lastname'][0].upper(
		) + user['lastname'][1:]
		
		# Every fields are required
		if not project_id or not reason:
			session['errormsg'] = "Error: You must fill out all the fields"
		
		
		if 'errormsg' not in session:
			# No error, insert data
			db.row_factory = make_dicts
			requeststatus = "Addressed"
			request_detail = "'{}', '{}', '{}', '{}', '{}'".format(
				user_name, full_name, project_id, reason, requeststatus)
			query_db("insert into Request (username, fullname, project, reason, requeststatus) values (" + request_detail + ");")
			db.commit()
			db.close()
			
			#Sucessfully Submit, show comfirmation
			errormsg = "You have successfully submit your remark request."
			return render_template('request.html', emsg = errormsg, grade_detail = grade_detail)
		else:
			return redirect('/requestremark')




## Only accessible by instructors pages

# Reviewfeedback page
@app.route('/reviewfeedback')
def reviewfeedback():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')

	#Get current user id
	user_name = session['username']

	db = get_db()
	db.row_factory = make_dicts
	# Get the uer infomation to identify the user authorization
	user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
	user_type = user_inf['usertype']
	# Block student from this page
	if user_type == "student":
		return redirect('/')
	greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]
	
	# Query from database
	feedback_detail = query_db('select * from Feedbacks where username = ?', [user_name], one = False)
	db.close()

	return render_template('reviewfeedback.html', parameters=[user_type, greeting_name, feedback_detail])


# Instructorgrade page
@app.route('/instructorgrade')
def instructorgrade():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')

	user_name = session['username']
	# Query from database
	db = get_db()
	db.row_factory = make_dicts
	user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
	grade_detail = query_db('select * from Grades', one = False)
	db.close()

	user_type = user_inf['usertype']
	# Block student from this page
	if user_type == "student":
		return redirect('/')
	greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]
	return render_template('instructorgrade.html', parameters=[user_type, greeting_name, grade_detail, grade_detail[0].keys()])



# Reviewrequest page
@app.route('/reviewrequest')
def reviewrequest():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')

	# Get current user id
	user_name = session['username']

	db = get_db()
	db.row_factory = make_dicts
	# Get the uer infomation to identify the user authorization
	user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
	user_type = user_inf['usertype']
	# Block student from this page
	if user_type == "student":
		return redirect('/')
	
	greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]
	
	# Query addressed request from database
	requeststatus = "Addressed"
	request_detail = query_db('select * from Request where requeststatus = ?',[requeststatus], one = False)
	for request_d in request_detail:
		request_d.pop('requeststatus')
	
	db.close()

	return render_template('reviewrequest.html', parameters=[user_type, greeting_name, request_detail])
	

# Updategrade page
@app.route('/updategrade', methods=['POST','GET'])
def updategrade():
	# Use session log_in determine log in or not
	if not session.get('log_in') or 'log_in' not in session:
		return redirect('/login')

	# GET
	if request.method == "GET":
		# Get current user id
		user_name = session['username']
		db = get_db()
		db.row_factory = make_dicts 

		# Get the uer infomation to identify the user authorization
		user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
		user_type = user_inf['usertype']

		# Block student from this page
		if user_type == "student":
			return redirect('/')

		greeting_name = user_inf['firstname'][0].upper(
			) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
			) + user_inf['lastname'][1:]
		
		# Query from database
		student_type = "student"
		student_detail = query_db('select * from Users where usertype = ?', [student_type], one = False)
		student_grades = query_db('select * from Grades', one = False)
		student_mark_dict = {}
		for student in student_detail:
			#set username in Users as key with following 
			student_mark_dict[student['username']] = [student['firstname'] + " " + student['lastname']]
		for student in student_grades:
			for key in student:
				if key != 'username':
					student_mark_dict[student['username']].append(student[key])
		db.close()

		return render_template("updategrade.html", parameters = [user_type, greeting_name, student_mark_dict])
	
	# POST
	if request.method == "POST":
		# request form
		user_name = request.form.get('username')
		assi_1 = int(request.form.get(user_name + '+A1'))
		assi_2 = int(request.form.get(user_name + '+A2'))
		assi_3 = int(request.form.get(user_name + '+A3'))
		assi_4 = int(request.form.get(user_name + '+A4'))
		term_1 = int(request.form.get(user_name + '+T1'))
		term_2 = int(request.form.get(user_name + '+T2'))
		term_3 = int(request.form.get(user_name + '+T3'))
		final_exam = int(request.form.get(user_name + '+FE'))



		# Commit data to database
		db = get_db()
		db.row_factory = make_dicts
		# Query remark request and grade from database
		change_list = [assi_1, assi_2, assi_3, assi_4, term_1, term_2, term_3, final_exam]
		object_list = ["A1", "A2", "A3", "A4", "T1", "T2", "T3", "finalexam"]
		request_list = query_db('select * from Request where username = ?', [user_name], one = False)
		grade_detail = query_db('select * from Grades where username = ?',[user_name], one = True)
		requeststatus = "Closed"
		i = 0
		while (i < len(change_list)):
			if change_list[i] == grade_detail[object_list[i]]:
				i+=1
			elif change_list[i] != grade_detail[object_list[i]]:
				for request_l in request_list:
					if request_l['project'] == object_list[i]:
						request_l['requeststatus'] = requeststatus
				i+=1


		sql_update = "UPDATE Grades SET A1 = {}, A2 = {}, A3 = {}, A4 = {}, T1 = {}, T2 = {}, T3 = {}, finalexam = {} WHERE username = '{}';".format(
			assi_1, assi_2, assi_3, assi_4, term_1, term_2, term_3, final_exam, user_name)

		query_db(sql_update, one = True)
		db.commit()
		db.close()
		return redirect("/updategrade")



# Searchstudent page
@app.route('/searchstudent')
def searchstudent():
	db = get_db()
	db.row_factory = make_dicts


	# Get current user id
	user_name = session['username']
	# Get the uer infomation to identify the user authorization
	user_inf = query_db('select * from Users where username = ?', [user_name], one = True)
	user_type = user_inf['usertype']

	# Block student from this page
	if user_type == "student":
		return redirect('/')

	greeting_name = user_inf['firstname'][0].upper(
		) + user_inf['firstname'][1:] + ' ' + user_inf['lastname'][0].upper(
		) + user_inf['lastname'][1:]

	# Query from database
	student_username = request.form.get("searchbox")
	student_detail = query_db('select * from Grades where username = ?', [student_username], one = True)
	student_inf = query_db('select * from Users where username = ?', [student_username], one = True)
	
	student_mark_dict = {}
	# Check if any student is founded
	if student_inf == None:
		return render_template('updategrade.html', parameters = [user_type, greeting_name, []])

	
	student_mark_dict[student_inf['username']] = [student_inf['firstname'] + " " + student_inf['lastname']]

	for key in student_inf:
		if key != 'username':
			student_mark_dict[student_inf['username']].append(student_inf[key])
	
	db.close()
	

	return render_template('updategrade.html', parameters = [user_type, greeting_name, student_mark_dict])




if __name__ == "main":
	# app.secret_key = "its a secret key" 
	app.config['SESSION_TYPE'] = 'filesystem'
	sess.init_app(app)
	app.run(debug=True)