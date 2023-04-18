<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="csrf-token" content="{{ csrf_token() }}">
        <title>@yield('title') | {{ config('backend.site.title') }}</title>

        <link rel="icon" type="image/x-icon" href="{{ Storage::url(config('backend.site.favicon')) }}" />

        <!-- Include Styles -->
        @include('backend/layouts/sections/styles')

    </head>

    <body class="hold-transition sidebar-mini layout-fixed">

        @yield('layoutContent')

        <!-- Include Scripts -->
        @include('backend/layouts/sections/scripts')

    </body>

</html>
