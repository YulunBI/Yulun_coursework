#include "knn.h"

/****************************************************************************/
/* For all the remaining functions you may assume all the images are of the */
/*     same size, you do not need to perform checks to ensure this.         */
/****************************************************************************/

/**************************** A1 code ****************************************/
typedef struct {
  int num_items;          // Number of images in the dataset
  unsigned char *labels;  // List of `num_items` labels [0-9]
  double *distance;        // Distance between each images and test image
} Kdataset;

/* Same as A1, you can reuse your code if you want! */
double distance(Image *a, Image *b) {
  // TODO: Return correct distance
  double total = 0;
  double xy = 0;
  double xx = 0;
  double yy = 0;
  for (int i = 0; i < ((a->sx) * (a->sy)); i++) {
    xy += a->data[i] * b->data[i];
    xx += a->data[i] * a->data[i];
    yy += b->data[i] * b->data[i];
    //total += pow(a->data[i] - b->data[i], 2);
  }
  total = (double)xy/(sqrt((double)xx) * sqrt((double)yy));
  //printf("distance: %f\n",total);
  return total;
}

int cmpfunc (const void * a, const void * b) {
  return ( *(unsigned char*)a - *(unsigned char*)b );
}

int search_the_freq_num(Kdataset *k_set) {
    
  int freq_of_label[2] = {0, 0}; // freq_of_label[0] stores the most frequency label, freq_of_label[1] stores the number of time it occurs.
  qsort(k_set->labels, k_set->num_items, sizeof(unsigned char), cmpfunc);
  //printf("\n\n freq_lab: %c\n",k_set->labels[0]);
  //printf("dis: %f",*k_set->distance);
  int count = 0;
  freq_of_label[0] = k_set->labels[0];
  //printf("%d\n",freq_of_label[0]);
  //printf("\n intv: %d\n",freq_of_label[0]);
  for (int i = 0; i < k_set->num_items; i++) {
    //printf("%d", k_set->labels[i]);
    if (k_set->labels[i] == freq_of_label[0]) {
      count ++;
    }
    else {
      if (count > freq_of_label[1]) {
        freq_of_label[1] = count;
        freq_of_label[0] = k_set->labels[i - 1];
      }
      count = 0;
    }
  }
  //printf("\n freq: %d\n",freq_of_label[0]);
  return freq_of_label[0];
}

/* Same as A1, you can reuse your code if you want! */
int knn_predict(Dataset *data, Image *input, int K) {
  // TODO: Replace this with predicted label (0-9)
  Kdataset *k_set = malloc(sizeof(Kdataset));
  k_set->num_items = K;
  k_set->labels = malloc(K * sizeof(unsigned char));
  k_set->distance = malloc(K * sizeof(double));
  for (int i = 0; i < data->num_items; i++) {
    if (data->images[i].sx == input->sx && data->images[i].sy == input->sy) {
      double dis = distance(&(data->images[i]), input);
      if (i < K) {
        k_set->distance[i] = dis;
        k_set->labels[i] = data->labels[i];
      }
      for (int j = 0; j < K; j++) {
        if (dis < k_set->distance[j]) {
          k_set->distance[j] = dis;
          k_set->labels[j] = data->labels[i];
          break;
        }
      }
    }
  }
    //printf("%f",*k_set->distance);
  int result = search_the_freq_num(k_set);
  free(k_set->labels);
  free(k_set->distance);
  free(k_set);
  return result;
}

/**************************** A2 code ****************************************/

/* Same as A2, you can reuse your code if you want! */
Dataset *load_dataset(const char *filename) {
  // TODO: Allocate data, read image data / labels, return
  Dataset *dataset = NULL;
  dataset = malloc(sizeof(Dataset));
  if (dataset == NULL) {
    perror("malloc memory for dataset");
    exit(1);
  }

  //open file to read and check for error
  FILE *datafile = fopen(filename, "rb");
  if (datafile == NULL) {
    perror("open dataset file");
    exit(1);
  }

  //set num_items, malloc images array and label array and check for error
  int num_items;
  if (fread(&num_items, sizeof(int), 1, datafile) != 1) {
    perror("read number of items from dataset file");
    exit(1);
  }
  dataset->num_items = num_items;
  dataset->images = malloc(sizeof(Image)*num_items);
  if (dataset->images == NULL) {
    perror("malloc memory for Image array");
    exit(1);
  }
  dataset->labels = malloc(sizeof(unsigned char)*num_items);
  if (dataset->labels == NULL) {
    perror("malloc memory for labels array");
    exit(1);
  }

  //load labels and images to dataset and check for error
  for (int i = 0; i < num_items; i++) {
    unsigned char label;
    if (fread(&label, sizeof(unsigned char), 1, datafile) != 1) {
      perror("read label from dataset file");
      exit(1);
    }
    dataset->labels[i] = label;

    dataset->images[i].sx = 28;
    dataset->images[i].sy = 28;
    dataset->images[i].data = malloc(sizeof(unsigned char) * 28 * 28);
    if (dataset->images[i].data == NULL) {
      perror("malloc memory for data");
      exit(1);
    }
    for (int j = 0; j < 28*28; j++) {
      unsigned char pixel;
      if (fread(&pixel, sizeof(unsigned char), 1, datafile) != 1) {
        perror("load pixel value of image");
        exit(1);
      }
      dataset->images[i].data[j] = pixel;
    }
  }
  if (fclose(datafile) == -1) {
    perror("close dataset file");
    exit(1);
  }
  return dataset;
}

/* Same as A2, you can reuse your code if you want! */
void free_dataset(Dataset *data) {
  // TODO: free data
  for (int i = 0; i < data->num_items; i++) {
    free(data->images[i].data);
  }
  free(data->images);
  free(data->labels);
  free(data);
  return;
}


/************************** A3 Code below *************************************/

/**
 * NOTE ON AUTOTESTING:
 *    For the purposes of testing your A3 code, the actual KNN stuff doesn't
 *    really matter. We will simply be checking if (i) the number of children
 *    are being spawned correctly, and (ii) if each child is recieving the 
 *    expected parameters / input through the pipe / sending back the correct
 *    result. If your A1 code didn't work, then this is not a problem as long
 *    as your program doesn't crash because of it
 */

/**
 * This function should be called by each child process, and is where the 
 * kNN predictions happen. Along with the training and testing datasets, the
 * function also takes in 
 *    (1) File descriptor for a pipe with input coming from the parent: p_in
 *    (2) File descriptor for a pipe with output going to the parent:  p_out
 * 
 * Once this function is called, the child should do the following:
 *    - Read an integer `start_idx` from the parent (through p_in)
 *    - Read an integer `N` from the parent (through p_in)
 *    - Call `knn_predict()` on testing images `start_idx` to `start_idx+N-1`
 *    - Write an integer representing the number of correct predictions to
 *        the parent (through p_out)
 */
void child_handler(Dataset *training, Dataset *testing, int K, 
                   int p_in, int p_out) {
  // TODO: Compute number of correct predictions from the range of data 
  //      provided by the parent, and write it to the parent through `p_out`.
  int start_idx;
  int N;
  // Read data from pipe 1
  if (read(p_in, &N, sizeof(int)) != sizeof(int)) {
    perror("read start_idx from parent");
    exit(1);
  }
  if (read(p_in, &start_idx, sizeof(int)) != sizeof(int)) {
    perror("read N from parent");
    exit(1);
  }

  int predict;
  int partial_truth = 0;
  // Calculate knn
  for (int i = start_idx; i < N; i++) {
    predict = knn_predict(training, &(testing->images[i]), K);
    if (predict == testing->labels[i]) {
      partial_truth ++;
    }

  }

  // write the result this child get to pipe 2
  if (write(p_out, &partial_truth, sizeof(int)) != sizeof(int)) {
    perror("Write partial number of tru prediction to pipe 2");
    exit(1);
  }
  // Free datasets
  free_dataset(training);
  free_dataset(testing);
  return;
}