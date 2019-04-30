<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="鲲之大一锅炖不下">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Kun 鲲大</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="/css/floating-labels.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function(){
            $("#join_as").bind("click",function () {
                window.location.replace("/register.html");
            });
        });
    </script>
</head>
<body>

    <form class="form-signin" action="/defaultLogin" method="post">
        <div class="text-center mb-4">
            <img class="mb-4" src="/favicon.ico" alt="鲲大" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal">鲲之大一锅炖不下</h1>
        </div>

  <#--      <div class="form-label-group">
            <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
            <label for="inputEmail">Email address</label>
        </div>-->

        <div class="form-label-group">
            <input type="text" id="inputAccount" class="form-control" placeholder="Account" required autofocus>
            <label for="inputAccount">Account</label>
        </div>

        <div class="form-label-group">
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <label for="inputPassword">Password</label>
        </div>

        <div class="form-label-group">
            <input type="text" id="inputImageCode" class="form-control" placeholder="Code" required>
            <label for="inputImageCode">Code</label>
        </div>

        <div class="form-label-group">
            <img src="/image"  class="img-fluid">
        </div>

        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" >Sign in</button>
        <button class="btn btn-lg btn-primary btn-block" type="button" id="join_as">Join as</button>
        <p class="mt-5 mb-3 text-muted text-center">&copy; 2019-2022</p>
    </form>

</body>
</html>