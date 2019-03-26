//Includes
#include <iostream>			//Cout,
#include <cstdlib>
#include <fstream>
#include <string>
#include <cmath>
#include <algorithm>		//Shuffle
#include <chrono>			//Time 
//#include <unistd.h>

#include <mpi.h>


//Genetic Algorithm Parameters
bool  Crossover_Bool;				//Flag for Crossover
float Crossover_Rate;				//Crossover parameter
bool  Mutation_Bool;				//Flag for Mutation
float Mutation_Rate;				//Mutation parameter
bool  Elitism_Bool;					//Flag for Elitism
int   Tournament_Size;				//Size for Tournament
int   Population_Size;				//Total Population
int   Population_Max_Generation;	//Last Generation
int   RNG_Seed;						//Seed for RNG purposes

char DataSource;
char DataFile_A[] = "att48.tsp";
char DataFile_B[] = "bays29.tsp";
char DataFile_C[] = "test.tsp";
char DataFile_D[] = "zi929.tsp";

int FileDimension;
double *FileMatrixPtr;


//Initializing the GA Parameters with some default values for ease of use
void Default_Parameters(){
	Crossover_Bool = true;
	Crossover_Rate = 0.9;
	Mutation_Bool = true;
	Mutation_Rate = 0.2;
	Elitism_Bool = true;
	Population_Size = 1000;
	Tournament_Size = 100;
	Population_Max_Generation = 100;
	
	//Randomizing and storing the seed
	RNG_Seed = (time(NULL));
	srand(RNG_Seed);
}//End Initialize_Parameters

//Function which will calculate the Euclidean Distance between (x1,y1) and (x2,y2)
double EuclideanDistance(int x1, int y1, int x2, int y2){
	return sqrt(pow(x1 - x2,2) + pow (y1 - y2,2));
}//End EuclideanDistance

//Read the information from the file into the system for algorithm
void ReadFile(char FileName[]){
	
	std::ifstream inFile;
	int FileType;

	inFile.open(FileName);
	inFile >> FileType;			//Determining if its a Distance Matrix or Coordinates
	inFile >> FileDimension;	//Determining the number of nodes
	
	double* DistanceMatrix = new double[FileDimension*FileDimension];

	if( FileType == 0 ){
		
		//Coordinate-Type
		
		//Reading the Coordinates
		double temp = 0;
		double CoordArr[FileDimension*2];
		for (int i = 0; i < FileDimension; i++) {
			for (int j = 0; j < 2; j++) {
				inFile >> temp;
				CoordArr[i*2 + j] = temp;
			}//End for
		}//End for
		
		//Calculating the Distance-Matrix
		for (int i = 0; i < FileDimension; i++) {
			for (int j = 0; j <= i; j++) {	//Computing only half the matrix
				
				DistanceMatrix[i*FileDimension + j] = EuclideanDistance(CoordArr[i*2 + 0],CoordArr[i*2 + 1],CoordArr[j*2 + 0],CoordArr[j*2 + 1]);
				DistanceMatrix[j*FileDimension + i] = DistanceMatrix[i*FileDimension + j];	//Mirroring the Matrix

			}//End for
		}//End for
		
		
	} else if (FileType == 1) {
		
		//Distance-Matrix-Type
		
		//Reading the Distance-Matrix
		double temp = 0;
		for (int i = 0; i < FileDimension; i++) {
			for (int j = 0; j < FileDimension; j++) {
				inFile >> temp;
				DistanceMatrix[i*FileDimension + j] = temp;
			}//End for
		}//End for
		
		//std::cout << (inFile.read()) << std::endl;
		
	} else if (FileType == 2) {
		
		//Coordinate-Type: from <www.math.uwaterloo.ca/tsp/world>
		
		double temp = 0;
		char* inGarb = new char[256];
		double CoordArr[FileDimension*2];

		//Skipping excess data
		for (int i = 0; i < 8; i++) {
			inFile.getline(inGarb,256);
		}//End for

		//Reading the Coordinates
		for (int i = 0; i < FileDimension; i++) {
			inFile >> temp;	//Skipping excess data
			for (int j = 0; j < 2; j++) {
				inFile >> temp;
				
				CoordArr[i*2 + j] = temp;
			}//End for
			
		}//End for
		
		//Calculating the Distance-Matrix
		for (int i = 0; i < FileDimension; i++) {
			for (int j = 0; j <= i; j++) {	//Computing only half the matrix
				
				DistanceMatrix[i*FileDimension + j] = EuclideanDistance(CoordArr[i*2 + 0],CoordArr[i*2 + 1],CoordArr[j*2 + 0],CoordArr[j*2 + 1]);
				DistanceMatrix[j*FileDimension + i] = DistanceMatrix[i*FileDimension + j];	//Mirroring the Matrix

			}//End for
		}//End for
		
		//NO TOUCH THIS MAN
		delete inGarb;

	}//End if
	
	FileMatrixPtr = DistanceMatrix;

}//End ReadFile

//Function to generate a random path through the TSP
int* PathRandom (int pathSize) {
	
	//Creating a returnable-Array
	int* newPath;
	newPath = new int [pathSize];
	
	//Initializing it in order
	for (int i = 0; i < pathSize; i++) {newPath[i] = i;}
	
	//Shuffling the path
	std::random_shuffle (&newPath[0], &newPath[pathSize]);
	
	//Returning the path
	return newPath;
	
}//End PathRandom

//Function to copy a given path and return a new instance of it
int* PathCopy (int pathSize, int path[]) {
	
	//Creating a returnable-Array
	int* newPath;
	newPath = new int [pathSize];
	
	//Copying
	for (int i = 0; i < pathSize; i++) {
		newPath[i] = path[i];
	}//End for
	
	//Returning the path
	return newPath;
	
}//End PathCopy

//Function to calculate the cost of a given path through the TSP
double PathCost(int pathSize, int path[]) {
	
	double sum = 0;
	int pathCurr;
	int pathNext;
	
	for (int i = 0; i < (pathSize-1); i++) {
		pathCurr = path[i];
		pathNext = path[i+1];
		sum = sum + *(FileMatrixPtr + (pathCurr*pathSize + pathNext));
	}//End for

	return sum;
	
}//End PathCost

//Function to print the given pop to the terminal
void printPop (int pathSize, int pop[]) {
	for (int i = 0; i < FileDimension; i++) {
		std::cout << pop[i] << ", ";
	}//End for
	std::cerr << std::endl;
}//End printPop

//Function to calculate the average cost of the given generations
double avgPathCost(int popSize, int* population[]){
	double result = 0;
	for (int i = 0; i < popSize; i++) { result = result + PathCost(FileDimension, population[i]); }
	result = result / popSize;
	return result;
}//End avgPathCost

//Function to return the index of the best pop in the given population
int bestPop(int popSize, int pathSize, int* population[]) {
	int bestIndex = 0;
	int bestVal = PathCost(pathSize, population[0]);
	int currVal;
	for (int i = 1; i < popSize; i++) {
		currVal = PathCost(pathSize, population[i]);
		if (bestVal > currVal) {
			bestVal = currVal;
			bestIndex = i;
		}//End if
	}//End 
	return bestIndex;
}//End bestPop

double bestCost(int popSize, int pathSize, int* population[]){
	return PathCost(pathSize, population[bestPop(popSize, pathSize, population)]);
}//End bestCost

//Function to Mutate the Pop -> MUTATES THE POP DIRECTLY... Might not work with the Parallel version but idk yet
void MutatePop(int pathSize, int pop[]) {
	
	double MutationDegree = ((double)(rand()%100))/100;			//Degree that the pop will mutate by
	int NumMutations = (int)(pathSize * MutationDegree);		//Number of mutations to perform
	
	//Initiate Protocol 'X-Men'
	int gene_A;
	int gene_B;
	int temp;
	for (int i = 0; i < NumMutations; i++) {
		
		//Picking the genes to swap
		gene_A = rand()%pathSize;
		gene_B = rand()%pathSize;
		
		//Ensuring no Non-Mutations
		if (gene_B == gene_A && gene_A != 0) 			{gene_B = gene_B - 1;}
		if (gene_B == gene_A && gene_A != pathSize - 1) {gene_B = gene_B + 1;}
		
		//Swapping values
		temp 		= pop[gene_A];
		pop[gene_A] = pop[gene_B];
		pop[gene_B] = temp;
		
	}//End for
	
}//End MutatePop

//Function to perform the tournament to decide who succeeds to the next generation
int Tournament(int popSize, int pathSize, int tourSize, int* population[]) {
	
	int Tournament_Champion = -1;
	int Tournament_Champion_Path = -1;
	int Tournament_Contender_Path = -1;
	
	//Picking the Tourney Participants
		//Generates an array from 1..Population_Size
		//Shuffles it, pick the first "tourSize" elements and thats the tournament
	int PopLine[popSize];
	for (int i = 0; i < popSize; i++) {PopLine[i] = i;}
	std::random_shuffle (&PopLine[0], &PopLine[popSize]);
	
	//FIGHT
	Tournament_Champion			= 0;
	Tournament_Champion_Path	= PathCost(pathSize, population[PopLine[0]]);
	for (int i = 1; i < tourSize; i++) {
		Tournament_Contender_Path = PathCost(pathSize, population[PopLine[i]]);
		if (Tournament_Contender_Path < Tournament_Champion_Path) {
			Tournament_Champion_Path 	= Tournament_Contender_Path;
			Tournament_Champion 		= i;
		}//End if
	}//End for
	
	//WE HAVE A WINNER
	return PopLine[Tournament_Champion];
	
}//End Tournament

//Function to perform the Crossover
void Crossover(int pathSize, int Point_A, int Point_B, int* population_OLD[], int* population_NEW[], int Parent_Index) {
	
	int* NewPop_A = new int [pathSize];
	int* NewPop_B = new int [pathSize];
	
	//Crossing
	for (int i = 0; i < pathSize; i++) {
		//Left of Boint A
		if (i <= Point_A) {
			NewPop_A[i] = population_OLD[Parent_Index]  [i];
			NewPop_B[i] = population_OLD[Parent_Index+1][i];
		//Between Point A and B
		} else if (i <= Point_B) {
			NewPop_A[i] = population_OLD[Parent_Index+1][i];
			NewPop_B[i] = population_OLD[Parent_Index]  [i];
		//Right of Point B
		} else {
			NewPop_A[i] = population_OLD[Parent_Index]  [i];
			NewPop_B[i] = population_OLD[Parent_Index+1][i];
		}//End if
	}//End for
	
	//Storing the children in our next generation
	population_NEW[Parent_Index]   = NewPop_A;
	population_NEW[Parent_Index+1] = NewPop_B;
	
}//End Crossover

//Genetic Algorithm
void GeneticAlgorithm() {
	
	/*
		Rough layout...
		
		Generate Initial Population
		
		For each Generation:
			
			Generate next Generation:
				If Elitism   -> Grab most fit candidate and carry them overflow
				If Crossover -> Randomly pair remaining population and generate 2 new people using  a Crossover Mask (Two-Point-Crossover, Single-Point-Crossover, Uniform-Order-Crossover... etc)
				If Mutation  -> Randomly mutate population
	*/

	//Instance Variables
	int* Current_Generation[Population_Size];
	int* Tour_Generation[Population_Size];
	int* Next_Generation[Population_Size];
	
	
	int Crossover_A;
	int Crossover_B;
	int* Crossover_Cradle[2];
	
	//Generating the Initial Population
	for (int i = 0; i < Population_Size; i++) {
		Current_Generation[i] = PathRandom(FileDimension);
		Next_Generation[i]    = PathCopy(FileDimension, Current_Generation[i]);
		Tour_Generation[i]    = PathCopy(FileDimension, Current_Generation[i]);
	}//End for
	
	//For each Generation
	for (int currGen = 0; currGen < Population_Max_Generation; currGen++) {
		
		if (currGen % 10 == 0) {
			std::cout << "Gen: " << currGen << ", Avg: " << avgPathCost(Population_Size, Current_Generation) << ", Best: " << bestCost(Population_Size, FileDimension, Current_Generation) << std::endl;
		}//End if
		
		
		//Elitism
		if (Elitism_Bool) {
			//Copying the best pop over to ensure we don't lose track of our best path so far
			Tour_Generation[0] = PathCopy(FileDimension, Current_Generation[bestPop(Population_Size, FileDimension, Current_Generation)]);
		}//End if
		
		//Tournament Selection
		for (int i = 0; i < Population_Size; i++) {
			if ((Elitism_Bool == false && i == 0) || (Elitism_Bool == true && i >= 1)) {
				//Performing tournaments to ensure better pops are more likely to reproduce
				Tour_Generation[i] = PathCopy(FileDimension, Current_Generation[Tournament(Population_Size, FileDimension, Tournament_Size, Current_Generation)]);
			}//End if
		}//End for

		
		//Crossover - Utilizing 2-Point Crossover
		std::random_shuffle (&Tour_Generation[0], &Tour_Generation[Population_Size]);		//Shuffle maybe not be nessecary...
		for (int i = 0; i < Population_Size; i += 2) {
			
			//Crossing if RNG
			if (rand()%100 < 100*Crossover_Rate) {
				
				//Picking my two Crossover points
				Crossover_A = rand()%(FileDimension*2/3) + 0;								//Between           [0...2/3*FileDimension)
				Crossover_B = rand()%(FileDimension - Crossover_A - 1) + (Crossover_A+1);	//Between (Crossover_A...FileDimension)
				
				//Cross the streams!
				Crossover(FileDimension, Crossover_A, Crossover_B, Tour_Generation, Next_Generation, i);
				
			} else {
				Next_Generation[i]   = PathCopy(FileDimension, Tour_Generation[i]);
				Next_Generation[i+1] = PathCopy(FileDimension, Tour_Generation[i+1]); 
			}//End if
			
			
			
		}//End for
		
		//Mutation
		for (int i = 0; i < Population_Size; i++) {
			//Mutating if RNG
			if (rand()%100 < 100*Mutation_Rate) {
				MutatePop(FileDimension, Next_Generation[i]);
			}//End if
		}//End for
		
		//Copying the Next_Generation into the Current_Generation
		for (int i = 0; i < Population_Size; i++) {
			delete Current_Generation[i];
			delete Tour_Generation[i];
			Current_Generation[i] = Next_Generation[i];
			//Current_Generation[i] = PathCopy(FileDimension, );
		}//End for
		
	}//End for
	
	
	
	//Deallocating memory  ***DO NOT REMOVE***
	for (int i = 0; i < Population_Size; i++) {
		//delete Current_Generation[i];
		delete Next_Generation[i];
		//delete Tour_Generation[i];
	}//End for
	
	
}//End GeneticAlgorithm

//Main method
int main(int argc, char *argv[]) {
	int userIn;
	
	std::cerr << "COSC  3P93 - Project Part 2: Genetic Algorithm (Serialized)" << std::endl;
	std::cerr << "" << std::endl;
	std::cerr << "Which TSP Dataset do you wish to use? " << std::endl;
	std::cerr << "1. Att (48) " << std::endl;
	std::cerr << "2. Bays (29) " << std::endl;
	std::cerr << "3. Test (5) " << std::endl;
	std::cerr << "4. ZI (929) " << std::endl;
	std::cin >> userIn;
	
	//Getting the start time of the Algorithm
	auto start = std::chrono::high_resolution_clock::now();

	//Reading the Data into th system
	if (userIn == 1){ 
		ReadFile(DataFile_A);
	} else if (userIn == 2){
		ReadFile(DataFile_B);
	} else if (userIn == 3){
		ReadFile(DataFile_C);
	} else if (userIn == 4){
		ReadFile(DataFile_D);
		
	} else {
		std::cerr << "Invalid input. Terminating execution." << std::endl;
		std::exit(1);
	}//End if

	Default_Parameters();

	//Starting the GA
	GeneticAlgorithm();

	//Deallocating memory ***DO NOT REMOVE***
	delete FileMatrixPtr;
	
	//Getting the end time of the Algorithm
    auto finish = std::chrono::high_resolution_clock::now();

	//Outputting the time
    std::cout << "Execution Time: " << std::chrono::duration_cast<std::chrono::milliseconds>(finish-start).count() << "ms\n";
	
}//End main



















