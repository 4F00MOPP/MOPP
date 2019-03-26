#include <iostream>
#include <unistd.h>
#include <cstdlib>
#include <signal.h>
#include <pthread.h>
#include "a.h"

volatile int target = 0;
volatile bool continuing;
static const int NUMTHREADS = 4;
pthread_mutex_t olock; //Dealing with occupied
pthread_mutex_t cout_lock; 
volatile int occupied;

//Doing the actual stuffs
void* hashwork(void* unnecessary) {
	unsigned long nonce;
	unsigned int  hash;
	while (continuing) {
		genULong(nonce); //Passing in the variable, which gets modified as the method takes the pointer to it
		hash = calchash(nonce);
		
		//If the number of leading zeroes in the hash is a correct value
		if (leadingZeroes(hash) >= target) {
			//Ensuring only 1 thread prints to cout at a time
			pthread_mutex_lock(&cout_lock);
			std::cout<<"Mining Successful: "<<std::endl;
			std::cout<<"\t";
			printSixtyFour(nonce);
			std::cout<<"\tHashed: ";
			printThirtyTwo(hash);
			std::cout<<std::endl;
			pthread_mutex_unlock(&cout_lock);
		}//End if
	}//End while
	
	//Gracefully shutting down the thread
	pthread_mutex_lock(&olock);
	occupied--;
	std::cout<<"Thread is done."<<std::endl;
	pthread_mutex_unlock(&olock);
	
	
}//End hashwork

//Prints word as a 32-bit binary number
void printThirtyTwo(unsigned int word) {
	for (int i=0;i<32;i++)
	std::cout<<(((0x80000000>>i)&word)?1:0);
	std::cout<<std::endl;
}//End printThirtyTwo

//Prints word as a 64-bit binary number
void printSixtyFour(unsigned long word) {
	for (int i=0;i<64;i++)
	std::cout<<(((0x8000000000000000>>i)&word)?1:0);
	std::cout<<std::endl;
}//End printSixtyFour

//Breaks nonce up into 16 4-bit tokens, to generate hash value
unsigned int calchash(unsigned long nonce) {
	unsigned int hash=0;
	for (int i=15;i>=0;i--) {
		hash=hash*17+((nonce>>(4*i))&0x0F);
	}//End for
	return hash;
}//End calchash

//Method to generate the nonce (Unsigned Long
void genULong(unsigned long &nonce) {
	nonce=0;
	for (int i=63;i>=0;i--) {
		nonce<<=1;
		nonce|=random()%2;
	}//End for
}//End genULong

//Method to return number of leading zeroes
int leadingZeroes(unsigned int value) {
	for (int i=0;i<32;i++)
		if ((value>>(31-i))&1) return i;
	return 32;
}//End leadingZeroes

//Method to request input from user
int menu() {
	int c = -1;
	std::cout<<"Enter Number of Leading Zeroes: ";
	std::cin >> c;
	return c;
}//End menu

//Method to handle a interrupt signal
void interrupted(int sig) { //Sig does not matter to us.
	std::cout<<"\n Computations are complete.\n Stopping..."<<std::endl;
	continuing = false;
}//End interrupted

//Main
int main () {
	srand(time(NULL));
	pthread_t ct[NUMTHREADS]; //Threadpool
	
	if (signal(SIGINT,interrupted)==SIG_ERR) {
		std::cout<<"Unable to change signal handler."<<std::endl;
		return 1;
	}//End if	
	
	int choice;
	while (true){
		choice = menu();
		if (choice <= 0){
			std::cout<<"Exiting"<<std::endl;
			break;
		}//End if	
		target = choice;
		continuing = true;
		
		//Initializing the threadpool
		for (int a = 0; a < NUMTHREADS; a++) {
			pthread_mutex_lock(&olock);
			
			pthread_create(&ct[a], NULL, &hashwork, NULL);
			occupied++;
			
			pthread_mutex_unlock(&olock);
		}//End for
		
		//Ensuring the program continues to run as long as any thread is running
		while (occupied > 0) {
			sleep(1);
		}//End while
		
	}//End while
	return 0;
}//End for




