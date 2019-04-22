#!/usr/bin/php-cgi
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/logo_small.png">

    <title>MOPP - View Results</title>

    <!-- Custom styles for this template -->
    <link href="./main.css" rel="stylesheet">

    <!-- Bootstrap core CSS -->
    <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>
<?php
include('login.php');
if (!isset($_SESSION['username'])) {
    array_push($arrayOfErrors, "you must be logged in first!");
    header('location: index.php');
}
?>

<?php
$num = 0;
$num2 = 0;
$projectArray = array(); //stores all projects by name, and file array
$resultsArray = array();
$studentinfoArray = array();
$analysisarray = array();
$fileArray = array();
$newarray = array();
$submissionID = $_POST["submissionID"];
$myFile = "./4F00_MOPP/log_" . $submissionID . ".txt";
    //$myFile = "./log_2168.txt";
$myFile = fopen($myFile, "r");
if ($myFile) {
    $buffer = fgets($myFile);
    while ($buffer !== false) {
        $position = strpos($buffer, "Percentage of Similarity");
        $projectposition = strpos($buffer, "Source Files Detected");
        $analysis = strpos($buffer, "Analysis");
        //$trial = strpos($buffer, "TRIAL");
        if ($position !== false) {
            $value = substr($buffer, $position + 26, 6);
            array_push($resultsArray, $value);
        }

        if ($projectposition !== false) {
            $buffer = fgets($myFile);
            $value = substr($buffer, 0);
            array_push($studentinfoArray, $value);
           // $createnewproject = new Project($value);

            $buffer = fgets($myFile);
//            while ($buffer !== "\n") {
//                $value = substr($buffer, 0);
//                $createnewproject->add_to_source_files($value);
//                $buffer = fgets($myFile);
//            }
           // array_push($projectArray, $createnewproject);//adds project to array of projects


        }
        if ($analysis !== false) {
            $buffer = fgets($myFile);
            $value = substr($buffer, 4);
            array_push($analysisarray, $value);
        }
        $buffer = fgets($myFile);

    }
}
fclose($myFile);

?>


<!--Container -->
<div class="container">
    <div class="row">
        <div class="col"></div>
        <div class="col-lg-8">
            <h2 class="display-4 text-center">Viewing Submissions For ID:<?php echo $submissionID ?></h2>
        </div>
        <div class="col"></div>
    </div>
    <div class="row">
        <div class="col"></div>
        <div class="col-lg-10">
            <div class="card cardBackgroundTile withBorder text-center m-3 mx-auto p-3">
                <div class="card-body">
                    <div class="row">
                        <div class="col"></div>
                        <div class="col">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th scope="col">ProjectName_StudentID_Student#</th>
                                    <th scope="col">Results</th>
                                </tr>
                                </thead>
                                <tbody>


                                <?php
                                //echo var_dump($fileArray[0]);
                                $num = 0;
                                $size = sizeof($resultsArray);
                                while ($size !== $num) {
                                    echo '<tr>';
                                    echo '<td>';
                                    echo $analysisarray[$num];
                                    echo '</td>';
                                    echo '<td><form method="post"> <button type="button" class="btn btn-secondary" data-toggle="modal"
                                                      data-target="#exampleModal"</form>';
                                    echo $resultsArray[$num];
                                    echo '</button></td>';
                                    echo '</tr>';
                                    $num++;
                                }
                                ?>


                                </tbody>
                            </table>
                        </div>
                        <div class="col"></div>
                    </div>
                    <a href="#" onclick="javascript:window.history.back(-1);return false;"><button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit">Back</button></a>
                        
                    
                    <a href="https://github.com/4F00MOPP/MOPP/blob/master/User%20Doc%20(WIP).docx" class="mt-5">Help</a>

                </div>

            </div>

        </div>
        <div class="col"></div>
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            Files Found to be similar:</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <table class="table table-striped">
                            <tbody>
                            <?php
                                $submissionID = $_POST["submissionID"];
                                $myFile = "./4F00_MOPP/log_" . $submissionID . ".txt";
                            $file = fopen($myFile, "r");
                            while (!feof($file)) {
                                echo fgets($file) . "<br>";
                            }
                            //echo fgets($file);
                            //echo '<br>';
                            fclose($file);
                            ?>


                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--        ./row-->


</div>
<!-- /.container -->

<!-- Bootstrap core JavaScript
================================================== -->
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
        integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
        crossorigin="anonymous"></script>
<script>
    window.jQuery || document.write('<script src="/bootstrap-4.0.0-alpha.6-dist/js/vendor/jquery.min.js"><\/script>')

</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
        integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
        crossorigin="anonymous"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>

<!-- Custom Java script      -->
<script src="./mainJS.js"></script>


</body>

</html>
