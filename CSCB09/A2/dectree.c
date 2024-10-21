#include "dectree.h"

/**
 * This function takes in the name of the binary file containing the data and
 * loads it into memory. The binary file format consists of the following:
 *
 *     -   4 bytes : `N`: Number of images / labels in the file
 *     -   1 byte  : Image 1 label
 *     - 784 bytes : Image 1 data (28x28)
 *          ...
 *     -   1 byte  : Image N label
 *     - 784 bytes : Image N data (28x28)
 *
 * You can simply set the `sx` and `sy` values for all the images to 28, we
 * will not test this with different image sizes.
 */
Dataset *load_dataset(const char *filename) {
  // TODO: Allocate data, read image data / labels, return
  //initialize dataset and check for error
  Dataset *dataset = NULL;
  dataset = malloc(sizeof(Dataset));
  if (dataset == NULL) {
    perror("malloc memory for dataset");
  }

  //open file to read and check for error
  FILE *datafile = fopen(filename, "rb");
  if (datafile == NULL) {
    perror("open dataset file %s");
  }

  //set num_items, malloc images array and label array and check for error
  int num_items;
  if (fread(&num_items, sizeof(int), 1, datafile) != 1) {
    perror("read number of items from dataset file");
  }
  dataset->num_items = num_items;
  dataset->images = malloc(sizeof(Image)*num_items);
  if (dataset->images == NULL) {
    perror("malloc memory for Image array");
  }
  dataset->labels = malloc(sizeof(unsigned char)*num_items);
  if (dataset->labels == NULL) {
    perror("malloc memory for labels array");
  }

  //load labels and images to dataset and check for error
  for (int i = 0; i < num_items; i++) {
    unsigned char label;
    if (fread(&label, sizeof(unsigned char), 1, datafile) != 1) {
      perror("read label from dataset file");
    }
    dataset->labels[i] = label;

    dataset->images[i].sx = 28;
    dataset->images[i].sy = 28;
    dataset->images[i].data = malloc(sizeof(unsigned char) * 28 * 28);
    if (dataset->images[i].data == NULL) {
      perror("malloc memory for data");
    }
    for (int j = 0; j < 28*28; j++) {
      unsigned char pixel;
      if (fread(&pixel, sizeof(unsigned char), 1, datafile) != 1) {
        perror("load pixel value of image");
      }
      dataset->images[i].data[j] = pixel;
    }
  }
  if (fclose(datafile) == -1) {
    perror("close dataset file");
  }
  return dataset;
}

/**
 * This function computes and returns the Gini impurity of the M images
 * (with their indices stored in the array) if they were to be split
 * based on the given pixel.
 * 
 * DO NOT CHANGE THIS FUNCTION; It is already implemented for you.
 */
double split_gini_impurity(Dataset *data, int M, int indices[M], int pixel) {
  int a_freq[10] = {0}, a_count = 0;
  int b_freq[10] = {0}, b_count = 0;
  for (int i = 0; i < M; i++) {
    int img_idx = indices[i];
    // The pixels are always either 0 or 255, but using < 128 for generality.
    if (data->images[img_idx].data[pixel] < 128)
      a_freq[data->labels[img_idx]]++, a_count++;
    else
      b_freq[data->labels[img_idx]]++, b_count++;
  }

  double a_gini = 0, b_gini = 0;
  for (int i = 0; i < 10; i++) {
    double a_i = ((double)a_freq[i]) / ((double)a_count);
    double b_i = ((double)b_freq[i]) / ((double)b_count);
    if (a_count) a_gini += a_i * (1 - a_i);
    if (b_count) b_gini += b_i * (1 - b_i);
  }
  // Weighted average of gini impurity of children
  return (a_gini * a_count + b_gini * b_count) / M;
}

/**
 * Given a subset of M images and their corresponding indices, find and return
 * the most frequent label and it's frequency. The last 2 parameters are
 * only for outputs.
 *
 * - The most frequent label should be stored in `*label`
 * - The frequency of this label should be stored in `*freq`
 * 
 * If multiple labels have the same maximal frequency, return the smallest one.
 */
void get_most_frequent(Dataset *data, int M, int indices[M], 
                       int *label, int *freq) {
  // TODO: Set the correct values and return
  *label = 0;
  *freq = 0;
  int freq_of_label[10] = {0};
  for (int i = 0; i < M; i++) {
    int current_label = data->labels[indices[i]];
    freq_of_label[current_label] ++;
  }
  for (int j = 0; j < 10; j++) {
    if (freq_of_label[j] > *freq) {
      *label = j;
      *freq = freq_of_label[j];
    }
  }
  return;
}

/**
 * Given a subset of M images and their corresponding indices, find and return
 * the best pixel to split the data based on. The best pixel is the one which
 * has the minimum Gini impurity as computed by `split_gini_impurity()`.
 * 
 * The return value should be a number between 0-783 (inclusive), representing
 *  the pixel the M images should be split based on.
 * 
 * If multiple pixels have the same minimal Gini impurity, return the smallest.
 */
int find_best_split(Dataset *data, int M, int indices[M]) {
  // TODO: Return the correct pixel 
  int min_pixel = 0;
  double min_gini = 1000;
  double current_gini;
  for (int i = 0; i < 784; i++) {
    current_gini = split_gini_impurity(data, M, indices, i);
    if (current_gini < min_gini) {
      min_pixel = i;
      min_gini = current_gini;
    }
  }
  return min_pixel;
}

//A helper function to help create node
DTNode *create_node() {
  DTNode *node = malloc(sizeof(DTNode));
  if (node == NULL) {
    perror("malloc memory for DTNode");
  }
  node->classification = -1;
  node->pixel = -1;
  node->left = NULL;
  node->right = NULL;
  return node;
}
/**
 * This is the recursive function that creates the Decision tree. In each
 * recursive call, we only want to consider some subset of the entire dataset
 * corresponding to the node. To represent this, we pass in an array of indices
 * of these images in the dataset, along with it's length M. Be careful to
 * allocate this on the array for any recursive calls made, and free it when
 * you're done with creating the tree. Here, you need to:
 *
 *    - Compute ratio of most frequent image in current call, terminate if >95%
 *    - Find the best pixel to split on using `split_gini_impurity()`
 *    - Split the data, allocate corresponding arrays, and recurse
 *    - In either case, allocate a new node, set the correct values and return
 *       - If it's a child, set `classification`, and both children = NULL.
 *       - If it's not a child, set `pixel` and `left`/`right` (recursively). 
 */
DTNode *make_helper(Dataset *data, int M, int indices[M]) {
  // TODO: COnstruct and return tree
  int label;
  int freq;
  get_most_frequent(data, M, indices, &label, &freq);
  if (freq == 0) {
    perror("find frequency of the most frequent label");
  }
  if (label < 0 || label > 9) {
    perror("find label of the most frequent label");
  }
  DTNode *node = create_node();

  if ((freq) / M   >=  TERMINATE_RATIO) {
    node->classification = label;
    return node;
  } 

  int pixel = find_best_split(data, M, indices);
  if (pixel < 0 || pixel > 783)  {
    perror("find pixel for best split");
  }
  node->pixel = pixel;
  int Mleft = 0;
  int Mright = 0;
  int *indices_left = malloc(sizeof(int) * M);
  if (indices_left == NULL) {
    perror("malloc memory for indices_left");
  }
  int *indices_right = malloc(sizeof(int) * M);
  if (indices_right == NULL) {
    perror("malloc memory for indices_right");
  }

  for (int i = 0; i < M; i++) {
    //left and right array
    if (data->images[indices[i]].data[pixel] < 128) {
      indices_left[Mleft] = indices[i];
      Mleft++;
    } 
    else {
      indices_right[Mright] = indices[i];
      Mright++;
    }
  }
  node->left = make_helper(data, Mleft, indices_left);
  node->right = make_helper(data, Mright, indices_right);
  free(indices_left);
  free(indices_right);

  return node;
}

/**
 * This is the function exposed to the user. All you should do here is set
 * up the `indices` array correctly for the entire dataset and call 
 * `make_helper()` with the correct parameters.
 */
DTNode *make_dec_tree(Dataset *data) {
  // TODO: Set up `indices` array, call `make_helper` and return tree.
  //     Make sure you free any data that is not needed anymore (hint)
  int num = data->num_items;
  int *index = malloc(sizeof(int) * num);
  if (index == NULL) {
    perror("malloc memory for index");
  }
  for (int i = 0; i < num; i++) {
    index[i] = i;
  }
  DTNode *root = make_helper(data, num, index);
  free(index);
  return root;
}
//A helper function that return the result leaf node
DTNode *dec_tree_search(DTNode *root, Image *img) {
  if (root == NULL || root->classification != -1) {
    return root;
  }
  if (img->data[root->pixel] < 128) {
    return dec_tree_search(root->left, img);
  }
  return dec_tree_search(root->right, img);
}
/**
 * Given a decision tree and an image to classify, return the predicted label.
 */
int dec_tree_classify(DTNode *root, Image *img) {
  // TODO: Return the correct label
  if (root == NULL) {
    perror("empty tree");
  }
  DTNode *result = dec_tree_search(root, img);
  return result->classification;
}

/**
 * This function frees the Decision tree.
 */
void free_dec_tree(DTNode *node) {
  // TODO: Free the decision tree
  if (node == NULL) {
    return;
  }
  free_dec_tree(node->left);
  free_dec_tree(node->right);
  free(node);
  return; 
}

/**
 * Free all the allocated memory for the dataset
 */
void free_dataset(Dataset *data) {
  // TODO: Free dataset (Same as A1)
  for (int i = 0; i < data->num_items; i++) {
    free(data->images[i].data);
  }
  free(data->images);
  free(data->labels);
  free(data);
  return;
}