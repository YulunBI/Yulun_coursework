#include "dectree.h"

// Makefile included in starter:
//    To compile:               make
//    To decompress dataset:    make datasets
//
// Or, to compile manually, use:
//    gcc -o classifier dectree.c classifier.c -g -Wall -std=c99 -lm
//
// Running decision tree generation / validation:
//    ./classifier datasets/training_data.bin datasets/testing_data.bin

/*****************************************************************************/
/* Do not add anything outside the main function here. Any core logic other  */
/* than what is described below should go in `dectree.c`. You've been warned!*/
/*****************************************************************************/

/**
 * main() takes in 2 command line arguments:
 *    - training_data: A binary file containing training image / label data
 *    - testing_data: A binary file containing testing image / label data
 *
 * You need to do the following:
 *    - Parse the command line arguments, call `load_dataset()` appropriately.
 *    - Call `make_dec_tree()` to build the decision tree with training data
 *    - For each test image, call `dec_tree_classify()` and compare the real 
 *        label with the predicted label
 *    - Print out (only) one integer to stdout representing the number of 
 *        test images that were correctly classified.
 *    - Free all the data allocated and exit.
 * 
 *  (You should for the most part be able to re-use code from A1 for this)
 */
int main(int argc, char *argv[]) {
  // TODO: Handle command line arguments
  if (argc != 3) {
    printf("This program need 2 file path to run\n");
    exit(1);
  }
  // TODO: Compute the total number of correct predictions
  int total_correct = 0;
  char train_file[strlen(argv[1]) + 1];
  char test_file[strlen(argv[2]) + 1];
  train_file[strlen(argv[1])] = '\0';
  test_file[strlen(argv[2])] = '\0';
  strncpy(train_file, argv[1], strlen(argv[1]));
  strncpy(test_file, argv[2], strlen(argv[2]));
  Dataset *training_data = load_dataset(train_file);
  Dataset *testing_data = load_dataset(test_file);
  DTNode *root = make_dec_tree(training_data);
  for (int i = 0; i < testing_data->num_items; i++) {
    int predictl = dec_tree_classify(root, &(testing_data->images[i]));
    int reall = testing_data->labels[i];
    if (predictl == reall) {
       total_correct++;
     }
   }
   free_dataset(training_data);
   free_dataset(testing_data);
   free_dec_tree(root);

  // Print out answer
  printf("%d\n", total_correct);
  return 0;
}