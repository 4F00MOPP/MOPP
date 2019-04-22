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

<!--Container -->
<div class="container">
    <div class="row">
        <div class="col"></div>
        <div class="col-lg-8">
            <h2 class="display-4 text-center">Viewing Submissions For Assignment 1</h2>
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
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Results</th>
                                </tr>
                                </thead>
                                <tbody>

                                <?php
                                $resultsArray = array();
                                $myFile = "./log_2168.txt";
                                $myFile = fopen($myFile, "r");
                                if ($myFile) {
                                    $buffer = fgets($myFile);
                                    while ($buffer !== false) {
                                        $position = strpos($buffer, "Percentage of Similarity");
                                        if ($position !== false) {
                                            $value = substr($buffer, $position + 26, 6);
                                            array_push($resultsArray, $value);
                                        }

                                        $buffer = fgets($myFile);
                                    }
                                }


                                fclose($myFile);

                                ?>


                                    <?php
                                    $num = 0;
                                    $size=sizeof($resultsArray);
                                    while ($size !== $num) {
                                        echo '<tr>';
                                        echo '<th scope="row">John Smith</th>';
                                        echo '<td>1234567</td>';
                                        echo '<td><button type="button" class="btn btn-secondary" data-toggle="modal"
                                                      data-target="#exampleModal">';
                                        echo $resultsArray[$num];
                                        $num++;
                                        echo '</button></td>';
                                        echo '</tr>';
                                    }
                                    ?>



                                </tbody>
                            </table>
                        </div>
                        <div class="col"></div>
                    </div>
                    <form class="form-logOut mx-auto" action="./professor_classView.php">
                        <button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit">Back</button>
                    </form>
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
                        <h5 class="modal-title" id="exampleModalLabel"><?php
                            $myFile = "./log_2168.txt";
                            $lines = file($myFile);//file in to an array

                            echo $lines[28];

                            ?></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">File Name : % Similar</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row"><?php
                                    $myFile = "./log_2168.txt";
                                    $lines = file($myFile);//file in to an array

                                    echo $lines[29];

                                    ?></th>
                            </tr>
                            <tr>
                                <th scope="row"><?php
                                    $myFile = "./log_2168.txt";
                                    $lines = file($myFile);//file in to an array

                                    echo $lines[30];

                                    ?></th>

                            </tr>
                            <tr>
                                <th scope="row"><?php
                                    $myFile = "./log_2168.txt";
                                    $lines = file($myFile);//file in to an array

                                    echo $lines[31];

                                    ?></th>

                            </tr>
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
