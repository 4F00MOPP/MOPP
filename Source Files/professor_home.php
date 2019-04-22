#!/usr/bin/php-cgi
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/logo_small.png">

    <title>MOPP - Home</title>

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
                            MOPP
                        </h1>
                    </div>
                    <div class="card-body">
                        <?php if (isset($_SESSION['active'])): ?>
                        <p class="lead text-center">
                            <?php echo $_SESSION['active'];
                    unset($_SESSION['active']);
                    ?>
                        </p>
                        <?php endif ?>
                        <div class="welcome">
                            <?php if (isset($_SESSION['username'])): ?>
                            <p>Welcome <em><?php echo $_SESSION['username']; ?></em></p>
                            <?php endif ?>
                            <p class="lead">Select a class that you are teaching:</p>
                            <form>

                                <select class="form-control" onchange="location = this.options[this.selectedIndex].value">
                                    <option value="./professor_classView.php">COSC 2P03</option>
                                    <option value="./professor_classView.php">COSC 2P05</option>
                                    <option value="./professor_classView.php">COSC 4F00</option>
                                </select>
                            </form>

                            <form class="form-logOut mx-auto" action="./index.php">
                                <a href="professor_home.php?logout='1'">
                                    <button class="btn btn-block btn-secondary mt-2 mx-auto" name="logout" type="submit">Log Out</button></a>
                            </form>
                            <div class="p-2"></div>
                            <a href="https://github.com/4F00MOPP/MOPP/blob/master/User%20Doc%20(WIP).docx" class="mt-5">Help</a>
                        </div>

                    </div>

                </div>
            </div>

            <div class="col"></div>
            <!--        ./row-->


        </div>
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
