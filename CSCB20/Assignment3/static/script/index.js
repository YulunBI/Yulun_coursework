document.querySelectorAll('.marks').forEach(Elementcell => Elementcell.addEventListener('change', UpdateMarks));

function UpdateMarks(elementChangedEvent){
    let clickedcell = elementChangedEvent.target.getAttribute('name');
    let username = clickedcell.split('+')[0]
    console.log(clickedcell)
    console.log(username)
    let class_name = ".changing-" + username
    console.log(document.getElementsByClassName(class_name))//can hide this line after test.
    document.querySelector(class_name).disabled = false;
    
}

