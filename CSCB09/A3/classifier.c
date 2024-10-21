#include "knn.h"

// Makefile included in starter:
//    To compile:               make
//    To decompress dataset:    make datasets
//
// Example of running validation (K = 3, 8 processes):
//    ./classifier 3 datasets/training_data.bin datasets/testing_data.bin 8

/*****************************************************************************/
/* This file should only contain code for the parent process. Any code for   */
/*      the child process should go in `knn.c`. You've been warned!          */
/*****************************************************************************/

/**
 * main() takes in 4 command line arguments:
 *   - K:  The K value for kNN
 *   - training_data: A binary file containing training image / label data
 *   - testing_data: A binary file containing testing image / label data
 *   - num_procs: The number of processes to be used in validation
 * 
 * You need to do the following:
 *   - Parse the command line arguments, call `load_dataset()` appropriately.
 *   - Create the pipes to communicate to and from children
 *   - Fork and create children, close ends of pipes as needed
 *   - All child processes should call `child_handler()`, and exit after.
 *   - Parent distributes the testing set among childred by writing:
 *        (1) start_idx: The index of the image the child should start at
 *        (2)    N:      Number of images to process (starting at start_idx)
 *     Each child should gets N = ceil(test_set_size / num_procs) images
 *      (The last child might get fewer if the numbers don't divide perfectly)
 *   - Parent waits for children to exit, reads results through pipes and keeps
 *      the total sum.
 *   - Print out (only) one integer to stdout representing the number of test 
 *      images that were correctly classified by all children.
 *   - Free all the data allocated and exit.
 */
int main(int argc, char *argv[]) {
  // TODO: Handle command line arguments
  if (argc !=5) {
    printf("This program need 4 arguments\n");
    exit(1);
  }
  char train_file[strlen(argv[3]) + 1];
  char test_file[strlen(argv[2]) + 1];
  train_file[strlen(argv[3])] = '\0';
  test_file[strlen(argv[2])] = '\0';
  strncpy(train_file, argv[3], strlen(argv[3]));
  strncpy(test_file, argv[2], strlen(argv[2]));
  int K = atoi(argv[1]);
  int num_procs = atol(argv[4]);
  if (num_procs <= 0) {
    printf("YNumber of Process Should be Positive\nm");
    exit(1);
  }
  // Load dataset
  Dataset *training_data = load_dataset(train_file);
  Dataset *testing_data = load_dataset(test_file);
  //printf("%d number of total test images\n",testing_data->num_items);
  // TODO: Spawn `num_procs` children
  // n stores children pid
  int n = 1;
  int pipe_write_to_c[num_procs][2];
  int pipe_read_from_c[num_procs][2];
  int N = ceil(testing_data->num_items / num_procs);
  if (N == 0) {
    N = testing_data->num_items;
  }
  int start_idx = 0;
	for(int i = 0; i < num_procs; i++) {
    // Create pipes which parent write to children, children reads from parent
    if (pipe(pipe_write_to_c[i]) == -1) {
      perror("Create pipe 1");
      exit(1);
    }
    // Create pipe which parent read from children, children write to parent
    if (pipe(pipe_read_from_c[i]) == -1) {
      perror("Create pipe 2");
      exit(1);
    }
    if (n > 0) {
      n = fork();
    }
		
		if (n < 0) {
			perror("fork");
			exit(1);
		} else if (n == 0) { // in child process
      // Close write end of pipe_write_to_c
      if (close(pipe_write_to_c[i][1]) == -1) {
        perror("Close write end of pipe 1");
        exit(1);
      }
      // Close read end of pipe_read_from_c
      if (close(pipe_read_from_c[i][0]) == -1) {
        perror("Close read end of pipe 2");
        exit(1);
      }
      // Close pipe we previously opened
      for (int l = 0; l < i; l++) {
        if (close(pipe_write_to_c[l][1]) == -1) {
          perror("close write end of pipe 1 of previously forked child");
          exit(1);
        }
        if (close(pipe_read_from_c[l][0]) == -1) {
          perror("close read end of pipe 2 of previously forked child");
          exit(1);
        }
      }
      child_handler(training_data, testing_data, K, pipe_write_to_c[i][0],pipe_read_from_c[i][1]);
      // Close the pipe we done with
      if (close(pipe_write_to_c[i][0]) == -1) {
        perror("Close read end of pipe 1 since we don't need it anymore");
        exit(1);
      }
      if (close(pipe_read_from_c[i][1]) == -1) {
        perror("Close write end of pipe 2 since we don't need it anymore");
        exit(1);
      }
      exit(0);
    } else { // in parent process
      // Close the pipe we don't need in parent
      if (close(pipe_write_to_c[i][0]) == -1) {
        perror("Close read end of pipe 1");
        exit(1);
      }
      if (close(pipe_read_from_c[i][1]) == -1) {
        perror("Close write end of pipe 1");
        exit(1);
      }
      // TODO: Send information to children
      // Write data to ith child
      if (write(pipe_write_to_c[i][1], &N, sizeof(int)) != sizeof(int)) {
        perror("write N to pipe");
        exit(1);
      }
      if (write(pipe_write_to_c[i][1], &start_idx, sizeof(int)) != sizeof(int)) {
        perror("write start_idx to pipe");
        exit(1);
      }

      start_idx += N;
      if ((start_idx + (N*2)) > testing_data->num_items) {
        N = testing_data->num_items - start_idx;
      } 
    }
 		
	}
  // Free database
  free_dataset(testing_data);
  free_dataset(training_data);
  // TODO: Compute the total number of correct predictions from returned values
  int total_correct = 0;
  int current_correct;

  for (int k = 0; k < num_procs; k++) {
      if (read(pipe_read_from_c[k][0], &current_correct, sizeof(int) != sizeof(int))) {
        perror("read result from child");
        exit(1);
      }

      total_correct += current_correct;
  }
    // Print out answer
  printf("%d\n", total_correct);
  return 0;
}
