# Word-Count-using-Map-Reduce-Programming-Paradigm

Developed a program based on Map/Reduce Programming Model. 

The purpose of the project is to find how many articles that contain a given specific keyword in the title or in the content. 

The input file is a text file with the size of 31 GB that is provided by Wikipedia Free Datasets (the entire Wikipedia content).

To run:

The Map/Reduce Program

o	cd pracgupt
o	ls -l
o	cd PrProj1
o	ls -l
o	vi WordFind.java

	Compile the Program:

o	mkdir ~/WordFind_Compile
o	javac -classpath $HADOOP_CLASSPATH'/*' -d ~/WordFind_Compile/ PrProj1/WordFind.java
o	jar -cvf ~/WordFind.jar -C ~/WordFind_Compile/ .

	Run Map/Reduce program with Pre-designed DataSet

o	less  /state/partition1/Sample_DataSets/Programming_Project_Dataset.txt
o	hadoop jar ~/WordFind.jar prachita.WordFind /CS5331_Examples/Programming_Project_Dataset.txt WordFind_Output_Keyword1 "texas tech"
                  		OR
o	hadoop jar ~/WordFind.jar prachita.WordFind /CS5331_Examples/Programming_Project_Dataset.txt WordFind_Output_Keyword2 "red raider"

	See the output of the Program:

o	hadoop fs -ls $MY_HADOOP_HOME/WordFind_Output_Keyword1
o	hadoop fs -cat /user/pracgupt/WordFind_Output_Keyword1/part-00000
				OR
o	hadoop fs -ls $MY_HADOOP_HOME/WordFind_Output_Keyword2
o	hadoop fs -cat /user/pracgupt/WordFind_Output_Keyword2/part-00000



	Delete the Output to let the Program Run Again:

o	hadoop fs -rm -r -skipTrash $MY_HADOOP_HOME/WordFind_Output_Keyword1
				OR
o	hadoop fs -rm -r -skipTrash $MY_HADOOP_HOME/WordFind_Output_Keyword2



