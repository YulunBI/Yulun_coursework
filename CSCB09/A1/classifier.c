#include "knn.h"

/**
 * Compilation command
 *    gcc -Wall -std=c99 -lm -o classifier classifier.c knn.c
 *
 * Decompress dataset into individual images:
 *    tar xvzf datasets.tgz
 *
 * Running quick test with 1k training and 1k testing images, K = 1:
 *    ./classifier 1 lists/training_1k.txt lists/testing_1k.txt
 *
 * Running full evaulation with all images, K = 7: (Will take a while)
 *    ./classifier 7 lists/training_full.txt lists/testing_full.txt
 */

/*****************************************************************************/
/* Do not add anything outside the main function here. Any core logic other  */
/* than what is described below should go into `knn.c`. You've been warned!  */
/*****************************************************************************/

/**
 * main() takes in 3 command line arguments:
 *    - K : The K value for K nearest neighbours 
 *    - training_list: Name of a text file with paths to the training images
 *    - testing_list:  Name of a text file with paths to the testing images
 *
 * You need to do the following:
 *    - Parse the command line arguments, call `load_dataset()` appropriately.
 *    - For each test image, call `knn_predict()` and compare with real label
 *    - Print out (only) one integer to stdout representing the number of 
 *        test images that were correctly predicted.
 *    - Free all the data allocated and exit.
 */
int main(int argc, char *argv[]) {
  // TODO: Handle command line arguments
    //printf("pass1");
    if (argc != 4) {
        exit(1);
    }
    int K = strtol(argv[1], NULL, 10);
    if (K <= 0) {
        exit(1);
    }
    //printf("pass2");
    char training_list[256];
    strcpy(training_list, argv[2]);
    char testing_list[256];
    strcpy(testing_list,argv[3]);
    Dataset *training_data = load_dataset(training_list);
    Dataset *testing_data = load_dataset(testing_list);
    //printf("pass3");
    
  // TODO: Compute the total number of correct predictions
    int total_correct = 0;
    for (int i = 0; i < testing_data->num_items; i++) {
        int my_result = knn_predict(training_data, &(testing_data->images[i]), K);
        //printf("my:%d real:%d\n", my_result,testing_data->labels[i]);
        if (my_result == testing_data->labels[i]) {
            total_correct ++;
            //printf("my:%d real:%d\n", my_result,testing_data->labels[i]);
        }
    }
    //printf("label[1]%c\n",training_data->labels[1]);
    //printf("label[2]%c\n",training_data->labels[2]);
    //printf("%d\n",training_data->images[0].sx);
    free_dataset(testing_data);
    free_dataset(training_data);
  // Print out answer
    printf("%d\n", total_correct);
    return 0;
}
