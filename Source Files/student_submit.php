#!/usr/bin/php-cgi

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/logo_small.png">

    <title>MOPP - Submit</title>

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
                <h2 class="display-4 text-center">Submit Assignment</h2>
            </div>
            <div class="col"></div>
        </div>
        <div class="row">
            <div class="col"></div>
            <div class="col-lg-10">
                <div class="card cardBackgroundTile withBorder text-center m-3 mx-auto p-3">
                    <div class="card-body">
                        <form class="form-submit mx-auto" action="uploadFile.php" method="post" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col">
                                    <label for="formGroupExampleInput" class="my-0 text-left">Course Code</label>
                                    <input type="text" class="form-control" placeholder="Ex: 2P03" name="courseCode" id="courseCode" required>

                                    <label for="formGroupExampleInput" class="my-0 text-left">Assignment Number</label>
                                    <input type="text" class="form-control" placeholder="Ex: 1" name="assNum" id="assNum" required>
                                </div>
                                <div class="col">
                                    <label for="formGroupExampleInput" class="my-0 text-left">User Name</label>
                                    <input type="text" class="form-control"  name="username"  id="username" placeholder="Ex: ab12xy">

                                    <label for="formGroupExampleInput" class="my-0 text-left">Student Number</label>
                                    <input type="text" class="form-control" placeholder="Ex: 1234567" name="stuNum" id="stuNum" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col text-left">
                                    <input type="file" class="form-control-file m-1" name="fileToUpload" id="fileToUpload">
                                </div>
                                <div class="col"></div>

                            </div>
                            <div class="row">
                                <div class="col">
                                    <input type="checkbox">
                                    <label class="form-check-label" for="exampleCheck1">I have never given nor recieved unacknowledged aid on this assignment.</label>
                                    <button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit" value="Upload file" name="submit">Submit</button>
                                    <div class="p-2"></div>
                                </div>
                            </div>

                        </form>
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
