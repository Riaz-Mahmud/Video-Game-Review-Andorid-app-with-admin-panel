<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login | {{ config('backend.site.title') }}</title>

        <link rel="icon" type="image/x-icon" href="{{ Storage::url('assets/images/favicon/favicon.png') }}" />

        @include('backend.pages.auth.include.css')

    </head>

    <body class="hold-transition login-page" style="background-image: url({{ Storage::url('assets/frontend/img/arabic-pattern.jpg') }}); background-size: cover; background-repeat: no-repeat; background-position: center;">
        <div class="login-box">
            <!-- /.login-logo -->
            <div class="card card-outline card-primary">
                <div class="card-header text-center">
                    <a href="{{ route('home')}}" class="h3">
                        <img src="{{ Storage::url('assets/images/logo/logo.png') }}" alt="logo" width="100%" />
                    </a>
                </div>
                <div class="card-body">
                    <p class="login-box-msg">Sign in to start your session</p>

                    @if ($errors->any() || session('error'))
                        @include('backend._partials.errorMsg')
                    @endif

                    @if (session('success'))
                        @include('backend._partials.successMsg')
                    @endif

                    <form method="POST" action="{{ route('login') }}">
                        @csrf
                        <div class="input-group mb-3">
                            <input class="form-control" placeholder="Email" type="email" name="email" :value="old('email')" required autofocus />
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                            <x-input-error :messages="$errors->get('email')" class="mt-2" />
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" class="form-control" placeholder="Password" type="password" name="password" required autocomplete="current-password" />
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                            <x-input-error :messages="$errors->get('password')" class="mt-2" />
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="icheck-primary">
                                    <input id="remember" type="checkbox" class="rounded border-gray-300 text-indigo-600 shadow-sm focus:ring-indigo-500" name="remember">
                                    <label for="remember">
                                        Remember Me
                                    </label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-4">
                                <button type="submit" class="btn btn-primary btn-block">Sign In</button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>


                </div>
                <!-- /.card-body -->
            </div>
            <!-- /.card -->
        </div>
        <!-- /.login-box -->

        @include('backend.pages.auth.include.js')

    </body>

</html>
