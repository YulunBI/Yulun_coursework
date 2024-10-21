#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main() {
    printf("print\n");
    FILE *f = fopen("datasets/training_data.bin","rb");
    if (f == NULL) {
        printf("error\n");
    }
    else {
        printf("success\n");
    }
    return 0;
}