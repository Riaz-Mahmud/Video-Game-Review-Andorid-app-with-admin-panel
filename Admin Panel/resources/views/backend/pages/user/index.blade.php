@extends('backend/layouts/master')

@section('title', 'User List')

@section('page-script')
    <script src="{{ asset('assets/backend/plugins/datatables/jquery.dataTables.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-responsive/js/dataTables.responsive.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-responsive/js/responsive.bootstrap4.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-buttons/js/dataTables.buttons.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-buttons/js/buttons.bootstrap4.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/jszip/jszip.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/pdfmake/pdfmake.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/pdfmake/vfs_fonts.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-buttons/js/buttons.html5.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-buttons/js/buttons.print.min.js') }}"></script>
    <script src="{{ asset('assets/backend/plugins/datatables-buttons/js/buttons.colVis.min.js') }}"></script>
    <script>
        $(function() {
            $("#datatables-users").DataTable({
                "responsive": true,
                "lengthChange": false,
                "autoWidth": false,
                "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
            }).buttons().container().appendTo('#datatables-users_wrapper .col-md-6:eq(0)');
        });

        function roleUpdate(id, role) {

            // rander option for select id chooseRole
            var options = '';
            options += '<option selected disabled>Choose Role</option>';
            options += '<option value="">N/A</option>';
            @foreach ($data['roles'] as $role )
                if(role == '{{$role->name}}'){
                    options += '<option value="{{$role->hashId}}" selected>{{$role->name}}</option>';
                }else{
                    options += '<option value="{{$role->hashId}}">{{$role->name}}</option>';
                }
            @endforeach

            $('#chooseRole').html(options);

            $('#upgradeRoleModalUserId').val(id);
            $('#upgradeRoleModal').modal('show');
        }

        // when click on upgradeRoleBtn
        $('#upgradeRoleBtn').click(function() {
            var userId = $('#upgradeRoleModalUserId').val();
            var roleId = $('#chooseRole').val();

            if (userId == '' ) {
                Toast.fire({
                    icon: 'error',
                    title: 'Please select a user'
                });
                return false;
            }

            $.ajax({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                },
                url: "{{ route('admin.role.update.user') }}",
                type: "POST",
                data: {
                    "userId": userId,
                    "roleId": roleId,
                },
                success: function(response) {
                    if (response.status == 'success') {
                        $('#upgradeRoleModal').modal('hide');

                        toastr.success(response.message);

                        // reload page
                        setTimeout(function() {
                            location.reload();
                        }, 800);

                    } else {
                        toastr.error(response.message);
                    }
                },
                error: function(response) {
                    toastr.error(response.message);
                }
            });
        });

    </script>
@endsection

@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">User List</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                            <li class="breadcrumb-item active">User List</li>
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

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">User List</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="datatables-users" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>User</th>
                                            <th>Role</th>
                                            <th>Phone</th>
                                            <th>Email</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @foreach ($data['users'] as $key => $user)
                                            <tr>
                                                <td>{{ $key + 1 }}</td>
                                                <td>
                                                    <div class="user-block">
                                                        <img class="img-circle" src="{{ $user['image'] }}"
                                                            alt="User Image">
                                                        <span class="username">
                                                            <a href="#">{{ $user['full_name'] }}</a>
                                                        </span>
                                                    </div>

                                                </td>
                                                <td>
                                                    @if ($user['role'] == null)
                                                        <span class="badge badge-danger">No Role</span>
                                                        <span class='badge badge-center rounded-pill bg-label-success w-px-30 h-px-30 me-2' id='roleEditSpan' style='cursor: pointer;' onclick='roleUpdate("{{ $user['hashId'] }}","{{ $user['role'] }}")'>
                                                            <i class='fas fa-plus'></i>
                                                            Add
                                                        </span>
                                                    @else
                                                        <span class="badge badge-success">{{ $user['role'] }}</span>
                                                        @if ($user['role'] != 'Super Admin')
                                                            <span class='badge badge-center rounded-pill bg-label-success w-px-30 h-px-30 me-2' id='roleEditSpan' onclick='roleUpdate("{{ $user['hashId'] }}","{{ $user['role'] }}")' style='cursor: pointer;'>
                                                                <i class='fas fa-edit'></i>
                                                                Edit
                                                            </span>
                                                        @endif
                                                    @endif
                                                </td>
                                                <td>{{ $user['phone'] }}</td>
                                                <td>{{ $user['email'] }}</td>
                                                <td>
                                                    @if ($user['status'] == 'Active')
                                                        <span class="badge badge-success">{{ $user['status'] }}</span>
                                                    @else
                                                        <span class="badge badge-danger">{{ $user['status'] }}</span>
                                                    @endif
                                                <td>
                                                    <a href="#"
                                                        class="btn btn-primary btn-sm">
                                                        <i class="fas fa-eye">
                                                        </i>
                                                        View
                                                    </a>
                                                </td>
                                            </tr>
                                        @endforeach

                                    </tbody>
                                </table>
                            </div>
                            <!-- /.card-body -->
                        </div>
                        <!-- /.card -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </section>
        <!-- /.content -->
    </div>

    @include('backend.pages.user.role_update')

@endsection
