#!/usr/bin/php-cgi
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/logo_small.png">

    <title>MOPP - Class View</title>

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
            <div class="col-lg-10">
                <div class="card cardBackgroundTile text-center m-3 mx-auto p-3">
                    <div class="card cardHeaderTile m-4 mx-auto">
                        <h1 class="heading_1 card-title MOPPHeader m-3">
                            2P05
                        </h1>
                    </div>
                    <div class="card-body">
                        <form class="form-submit mx-auto" action="selectFile.php" method="post" enctype="multipart/form-data">
                            <button class="btn btn-block btn-secondary m-2 mx-auto" type="submit" value="Select Files" name="submit">Select Files To Process</button>
                        </form>

                        <form class="form-submit mx-auto">
                            <button type="button" class="btn btn-block btn-secondary m-2 mx-auto" data-toggle="modal" data-target="#subID">View Results</button>
                        </form>
                        <div class="modal fade" id="subID" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Enter Submission ID</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="form-submit mx-auto" method="POST" action="./testing_ViewResults.php">
                                            <label for="formGroupExampleInput" class="my-0 text-left">Submission ID</label>
                                            <input type="text" class="form-control" placeholder="Ex: 2168" name="submissionID" id="submissionID" required>

                                            <button type="submit" class="btn btn-block btn-secondary m-2 mx-auto">View Results</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <form class="form-logOut mx-auto" action="./index.php">
                            <a href="professor_home.php?logout='1'">
                                <button class="btn btn-block btn-secondary mt-2 mx-auto" name="logout" type="submit">Log Out</button></a>
                        </form>
                        <div class="p-2"></div>
                        <a href="https://github.com/4F00MOPP/MOPP/blob/master/User%20Doc%20(WIP).docx" class="mt-5">Help</a>
                    </div>

                </div>

            </div>
            <div class="col"></div>
        </div>
        <!--        ./row-->




    </div>
    <!-- /.container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>
        window.jQuery || document.write('<script src="/bootstrap-4.0.0-alpha.6-dist/js/vendor/jquery.min.js"><\/script>')

    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="./bootstrap/js/bootstrap.min.js"></script>

    <!-- Custom Java script      -->
    <script src="./mainJS.js"></script>



</body>

</html>
