#!/usr/bin/php-cgi

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/logo_small.png">

    <title>MOPP - Bulk Assignment Submit</title>

    <!-- Custom styles for this template -->
    <link href="./main.css" rel="stylesheet">

    <!-- Bootstrap core CSS -->
    <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>

    <!--Container -->
    <div class="container">
        <div class="row">
            <div class="col"></div>
            <div class="col-lg-8">
                <h2 class="display-4 text-center">Bulk Assignment Submission</h2>
            </div>
            <div class="col"></div>
        </div>
        <div class="row">
            <div class="col"></div>
            <div class="col-lg-10">
                <div class="card cardBackgroundTile withBorder text-center m-3 mx-auto p-3">
                    <div class="card-body">
                        <form class="form-submit mx-auto" action="bulkUpload.php" method="post" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col text-left">
                                    <input type="file" class="form-control-file m-1" name="fileToUpload" id="fileToUpload">
                                </div>
                                <div class="col"></div>

                            </div>
                            <div class="row">
                                <div class="col">
                                    <button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit" value="Upload file" name="submit">Submit</button>
                                    <div class="p-2"></div>
                                </div>
                            </div>

                        </form>

                        <form class="form-submit mx-auto" action="bulkSelect.php" method="post" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col">
                                    <button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit" value="Upload file" name="submit">Process Files</button>
                                    <div class="p-2"></div>
                                </div>
                            </div>

                        </form>

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
