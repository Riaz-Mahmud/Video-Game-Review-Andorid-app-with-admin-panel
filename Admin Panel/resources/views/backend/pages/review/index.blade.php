@extends('backend/layouts/master')

@section('title', 'Review List')

@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">Review List</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                            <li class="breadcrumb-item active">Review List</li>
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
                                <h3 class="card-title">Pending Review List</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="datatables-users" class="table table-bordered table-striped" style="width: 100%">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>User Name</th>
                                            <th>Games Name</th>
                                            <th>Comment</th>
                                            <th>Rating</th>
                                            <th>Location</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @forelse ($data['rows'] as $key => $item)
                                            <tr>
                                                <td>{{ $key + 1 }}</td>
                                                <td width="20%">
                                                    {{ $item['user']['full_name'] }}
                                                </td>
                                                <td class="text-justify" width="10%">
                                                    {{ $item['game']['name'] }}
                                                </td>
                                                <td class="text-center" width="20%">
                                                    {{ $item['comments'] }}
                                                </td>
                                                <td class="text-center" width="5%">
                                                    {{ $item['rating'] }} <i class="fa fa-star text-warning"></i>
                                                </td>
                                                <td class="text-center" width="20%">
                                                    {{ $item['address'] }}
                                                </td>
                                                <td class="text-center" width="20%">
                                                    <form action="{{ route('admin.review.update.status', $item['hashId']) }}" method="POST" class="d-inline-block">
                                                        @csrf
                                                        <input type="hidden" name="status" value="Active">
                                                        <button type="submit" class="btn btn-sm btn-primary mb-1 mt-1" onclick="return confirm('Are you sure you want to approve this item?');" title="Approve">
                                                            <i class="fa fa-check text-white"></i>
                                                            Approve
                                                        </button>
                                                    </form>
                                                    <form action="{{ route('admin.review.delete', $item['hashId']) }}" method="POST" class="d-inline-block">
                                                        @csrf
                                                        <button type="submit" class="btn btn-sm btn-danger mb-1 mt-1" onclick="return confirm('Are you sure you want to delete this item?');" title="Delete">
                                                            <i class="fa fa-trash text-white"></i>
                                                            Delete
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        @empty
                                            <tr>
                                                <td colspan="7" class="text-center">
                                                    <h4>No data found</h4>
                                                </td>
                                            </tr>
                                        @endforelse

                                    </tbody>
                                </table>
                                {{-- paggination --}}
                                <div class="d-flex justify-content-center">
                                    {{ $data['rows']->links() }}
                                </div>
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
