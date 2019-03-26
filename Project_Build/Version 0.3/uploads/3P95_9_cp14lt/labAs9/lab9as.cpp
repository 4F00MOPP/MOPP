#include <iostream>
#include <unistd.h>
#include <cstdlib>
#include <signal.h>
#include <pthread.h>
#include <cmath>

double bestX = 0;
double bestY = 0;
double numClimbers = 1;

volatile int lazyCounter;
volatile double bestVal = 0;
volatile bool defaultBest = true;
volatile int occupied;
volatile bool continuing;
pthread_mutex_t olock; //Dealing with occupied
pthread_mutex_t vlock; //Dealing with occupied
pthread_mutex_t cout_lock; 

int calcFormula (int x, int y) {
	return (-(y + 47) * std::sin(std::sqrt(std::abs(x/2 + y + 47))) - x * std::sin(std::sqrt(std::abs(x - y - 47))));
}//End calcFormula

double randDouble (int min, int max) {
    double f = (double)rand() / RAND_MAX;
    return (min + (f * (max - min)));
}//End randDouble

void* climbWork(void* unnecessary) {
	double startLoc = lazyCounter * (1024/numClimbers);
	double currX = startLoc;
	double currY = startLoc;
	double currV;
	double xMut;
	double yMut;
	double vMut;
	double tempX = currX;
	double tempY = currY;
	bool moved;
	
	while (continuing) {
		moved = false;
		//Check 4 steps and update
		for (int a  = 0; a < 4; a++) {
			currV = calcFormula(currX,currY);
			xMut = currX + randDouble(-5,5);
			yMut = currY + randDouble(-5,5);
			
			//Ensuring we dont do anything out of bounds
			if (!(xMut < -512 || xMut > 512 || yMut < -512 || yMut > 512)){
				vMut = calcFormula(xMut,yMut);
				//Updating the local best
				if (vMut < currV){
					moved = true;
					tempX = xMut;
					tempY = yMut;
				}//End if
			}//End if
		}//End for
		
		//Refreshing the value if the x,y were changed
		if (moved == true) {
			currX = tempX;
			currY = tempY;
			currV = calcFormula(currX,currY);
		}//End if
		
		//Updating the best location and value variables
		if (defaultBest == true || bestVal > currV){
			pthread_mutex_lock(&vlock);
			defaultBest = false;
			bestVal = currV;
			bestX = currX;
			bestY = currY;
			std::cout<<"\nNew Global Best found: "<<std::endl;
			std::cout<<"New Best Found: "<<currV<<", X: "<<currX<<", Y: "<<currY<<std::endl;
			pthread_mutex_unlock(&vlock);
		}//End if
		
		//Randomizing the location if no move was taken
		if (moved == false) {
			currX = randDouble(-512,512);
			currY = randDouble(-512,512);
		}//End if
		
		//Update the 
	}//End while
	
	//Gracefully shutting down the thread
	pthread_mutex_lock(&olock);
	occupied--;
	pthread_mutex_unlock(&olock);
	
	
}//End hashwork

int menu() {
	int c = -1;
	std::cout<<"Enter Number of climbers: (1-8, 0 to stop) ";
	std::cin >> c;
	return c;
}//End menu

//Method to handle a interrupt signal
void interrupted(int sig) { //Sig does not matter to us.
	std::cout<<"\n Interrupt Signalled.\n Stopping..."<<std::endl;
	continuing = false;
}//End interrupted

int main () {
	srand(time(NULL));
	pthread_t ct[8]; //Threadpool
	
	if (signal(SIGINT,interrupted)==SIG_ERR) {
		std::cout<<"Unable to change signal handler."<<std::endl;
		return 1;
	}//End if	
	
	while (numClimbers != 0) {
		numClimbers = menu();
		continuing = true;
		if (numClimbers > 0 && numClimbers < 9){
			//Do the shizz
			
			//Initializing the threadpool
			for (int a = 0; a < numClimbers; a++) {
				pthread_mutex_lock(&olock);
				lazyCounter = a;
				pthread_create(&ct[a], NULL, &climbWork, NULL);
				occupied++;
				pthread_mutex_unlock(&olock);
			}//End for
			
			//Ensuring the program stays running while threads are active
			while (occupied > 0) {
				sleep(1);
			}//End while
			
		}//End if
	}//End while
	std::cout<<"0 Selected. Ending program."<<std::endl;
}//End main