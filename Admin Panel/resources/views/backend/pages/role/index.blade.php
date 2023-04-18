@extends('backend/layouts/master')

@section('title', 'Role List')


@section('page-script')
    <script src="{{ asset('assets/backend/vendor/libs/formvalidation/dist/js/FormValidation.min.js') }}"></script>
    <script src="{{ asset('assets/backend/vendor/libs/formvalidation/dist/js/plugins/Bootstrap5.min.js') }}"></script>
    <script src="{{ asset('assets/backend/vendor/libs/formvalidation/dist/js/plugins/AutoFocus.min.js') }}"></script>

    <script src="{{ asset('assets/backend/js/modal-add-role.js') }}"></script>

    <script>
        function editRole(id, name, permissions) {
            // reset form
            $('#addRoleForm').trigger('reset');
            // reset permissions
            $('#addRoleForm input[name="permissions[]"]').prop('checked', false);

            // set form action
            $('#addRoleForm').attr('action', '{{ route('admin.role.update', ':id') }}'.replace(':id', id));
            // set name value
            $('#addRoleForm input[name="name"]').val(name);
            // show modal
            $('#addRoleModal').modal('show');

            // set permissions
            var permissions = JSON.parse(permissions);
            $.each(permissions, function(index, value) {
                $('#addRoleForm input[name="permissions[]"][value="' + value + '"]').prop('checked', true);
            });

        }

        function addRole() {
            // reset form
            $('#addRoleForm').trigger('reset');
            // reset permissions
            $('#addRoleForm input[name="permissions[]"]').prop('checked', false);

            // set form action
            $('#addRoleForm').attr('action', '{{ route('admin.role.store') }}');
            // set name value
            $('#addRoleForm input[name="name"]').val('');
            // show modal
            $('#addRoleModal').modal('show');
        }
    </script>

@endsection

@section('content')

    <div class="content-wrapper">

        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">Role List</h1>
                        <p>A role provided access to predefined menus and features so that depending on <br> assigned role an administrator can
                            have access to what user needs.</p>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                            <li class="breadcrumb-item active">Role List</li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        @if ($errors->any() || session('error'))
            @include('backend._partials.errorMsg')
        @endif

        @if (session('success'))
            @include('backend._partials.successMsg')
        @endif

        <section class="content">
            <div class="container-fluid">
                <!-- Role cards -->
                <div class="row g-4">
                    @foreach ($data['roles'] as $role)
                        <div class="col-xl-4 col-lg-6 col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between mb-2">
                                        <h6 class="fw-normal">Total {{ $role->userCount }} users</h6>
                                        @if (count($role->users) > 0)
                                            <ul class="list-unstyled d-flex align-items-center avatar-group mb-0">
                                                @foreach ($role->users as $user)
                                                    @if ($loop->index < 5)
                                                        <li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top"
                                                            title="{{ $user->first_name }}" class="avatar avatar-sm pull-up">
                                                            <img class="rounded-circle" src="{{ $user->image }}" alt="Avatar" title="{{ $user->first_name }}" />
                                                        </li>
                                                    @endif
                                                @endforeach
                                            </ul>
                                        @endif
                                    </div>
                                    <div class="d-flex justify-content-between align-items-end">
                                        <div class="role-heading">
                                            <h4 class="mb-1">{{ $role->name }}</h4>
                                            @if ($role->id != 1)
                                                @can('admin.role.edit')
                                                    <a href="javascript:;"
                                                        onclick="editRole('{{ $role->hashId }}','{{ $role->name }}','{{ json_encode($role->permissions) }}')"><small>Edit
                                                            Role</small></a>
                                                @endcan

                                                @if (auth()->user()->can('admin.role.edit') &&
                                                    auth()->user()->can('admin.role.delete'))
                                                    <div class="vr"></div>
                                                @endif

                                                @can('admin.role.delete')
                                                    <form action="{{ route('admin.role.delete', $role->hashId) }}" method="POST"
                                                        class="d-inline">
                                                        @csrf
                                                        <button type="submit" class="btn btn-link text-danger p-0 m-0 align-baseline"
                                                            onclick="return confirm('Are you sure you want to delete this role?');"><small>Delete
                                                                Role </small></button>
                                                    </form>
                                                @endcan
                                            @endif
                                            @if ($role->id == 1)
                                                <small>Super Admin have all the permissions</small>
                                            @endif
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    @endforeach
                    @can('admin.role.create')
                        <div class="col-xl-4 col-lg-6 col-md-6">
                            <div class="card h-100">
                                <div class="row h-100">
                                    <div class="col-sm-5">
                                        <div class="d-flex align-items-end h-100 justify-content-center mt-sm-0 mt-3">
                                            <img src="{{ asset('assets/backend/img/illustrations/sitting-girl-with-laptop-light.png') }}"
                                                class="img-fluid" alt="Image" width="120"
                                                data-app-light-img="illustrations/sitting-girl-with-laptop-light.png"
                                                data-app-dark-img="illustrations/sitting-girl-with-laptop-dark.png">
                                        </div>
                                    </div>
                                    <div class="col-sm-7">
                                        <div class="card-body text-sm-end text-center ps-sm-0">
                                            <button onclick="addRole()" class="btn btn-primary mb-3 text-nowrap">Add New Role</button>
                                            <p class="mb-0">Add role, if it does not exist</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    @endcan

                </div>
                <!--/ Role cards -->
            </div>
        </section>
    </div>

    <!-- Add Role Modal -->
    @include('backend/_partials/modal-add-role')
    <!-- / Add Role Modal -->
@endsection
