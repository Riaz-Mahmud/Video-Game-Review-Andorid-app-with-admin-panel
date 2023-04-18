@extends('backend/layouts/app')

@section('layoutContent')

    <div class="wrapper">
        <!-- Preloader -->
        <div class="preloader flex-column justify-content-center align-items-center">
            <img class="animation__shake" src="{{ Storage::url('assets/images/logo/logo.png') }}" alt="AdminLTELogo"
                height="80px" width="auto">
        </div>

        @include('backend/layouts/sections/navbar/navbar')

        @include('backend/layouts/sections/sidebar/sidebar')

        @yield('content')

        @include('backend/layouts/sections/footer/footer')

        <!-- Control Sidebar -->
        <aside class="control-sidebar control-sidebar-dark">
            <!-- Control sidebar content goes here -->
        </aside>
        <!-- /.control-sidebar -->

    </div>
@endsection
