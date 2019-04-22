<?php

class Project{
    var $projectnumber="";
    var $sourcefilesdetectedarray=array();


    //constructor, takes in an int for $projectnumber and an array for $sourcefilesdetected ?possibily an empty array to add to later
    public function __construct($projectnumber){
        $this-> projectnumber=$projectnumber;
       // $sourcefilesdetectedarray=array();//creates new empty array
        //$this-> sourcefilesdetectedarray[] = $filearray;//array of source files if present
    }

    public function get_project_number(){
        return $this-> projectnumber;//not needed?
    }

    //returns all source files in a given project
    public function get_project_contents($projectnumber){
        return $this-> sourcefilesdetectedarray;
    }

    public function add_to_source_files($file){
        array_push($this-> sourcefilesdetectedarray,$file);
    }
}
?>