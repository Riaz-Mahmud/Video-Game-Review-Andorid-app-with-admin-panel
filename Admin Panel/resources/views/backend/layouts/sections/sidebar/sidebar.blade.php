<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="index3.html" class="brand-link">
        <img src="{{ Storage::url(config('backend.site.favicon')) }}" alt="AdminLTE Logo"
            class="brand-image img-circle elevation-3" style="opacity: .8;">
        <span class="brand-text font-weight-light">{{ config('backend.site.short_title') }}</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src=" {{ \Facades\App\Helpers\ImageHelper::getProfileImage(Auth::user(),'thumbanil') }} "class="img-circle elevation-2"
                    alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">{{ Auth::user()->first_name }}</a>
            </div>
        </div>

        <!-- SidebarSearch Form -->
        <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search"
                    aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu"
                data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
             with font-awesome or any other icon font library -->
                <li class="nav-item">
                    <a href="{{ route('admin.dashboard') }}"
                        class="nav-link @if (Route::currentRouteName() == 'admin.dashboard') active @endif">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>
                            Dashboard
                        </p>
                    </a>
                </li>
                <li class="nav-item {{ Route::is('admin.games.*')  ? 'menu-open' : '' }}">
                    <a href="#" class="nav-link {{ Route::is('admin.games.*')  ? 'active' : '' }}">
                        <i class="nav-icon fas fa-book"></i>
                        <p>
                            Games
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="{{ route('admin.games.index') }}"
                                class="nav-link @if (Route::currentRouteName() == 'admin.games.index') active @endif">
                                <i class="far fa-circle nav-icon"></i>
                                <p>List</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="{{ route('admin.games.create') }}"
                                class="nav-link @if (Route::currentRouteName() == 'admin.games.create') active @endif">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Create</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a href="{{ route('admin.review.index') }}"
                        class="nav-link @if (Route::currentRouteName() == 'admin.review.index') active @endif">
                        <i class="nav-icon fas fa-star"></i>
                        <p>
                            Reviews
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="{{ route('admin.user.index') }}"
                        class="nav-link @if (Route::currentRouteName() == 'admin.user.index') active @endif">
                        <i class="nav-icon fas fa-users"></i>
                        <p>
                            User
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="{{ route('admin.role.index') }}"
                        class="nav-link @if (Route::currentRouteName() == 'admin.role.index') active @endif">
                        <i class="nav-icon fas fa-shield-alt"></i>
                        <p>
                            Role & Permission
                        </p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
