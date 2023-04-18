@extends('backend/layouts/master')

@section('title', 'Games List')

@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">Games List</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                            <li class="breadcrumb-item active">Games List</li>
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
                                <h3 class="card-title">Games List</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="datatables-users" class="table table-bordered table-striped" style="width: 100%">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Games Name</th>
                                            <th>Description</th>
                                            <th>Rating</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @foreach ($data['rows'] as $key => $item)
                                            <tr>
                                                <td>{{ $key + 1 }}</td>
                                                <td width="20%">
                                                    <a href="{{URL::to($item->slug)}}" target="_blank">
                                                        {{ $item['name'] }}
                                                    </a>
                                                </td>
                                                <td class="text-justify" width="30%">
                                                    {!! $item['description'] !!}
                                                </td>
                                                <td class="text-center" width="10%">
                                                    {{ $item['rating'] }}({{ $item['rating_count'] }}) <i class="fa fa-star text-warning"></i>
                                                </td>
                                                <td class="text-center" width="10%">
                                                    @if ($item['status'] == 'Active')
                                                        <span class="badge badge-success">{{ $item['status'] }}</span>
                                                    @else
                                                        <span class="badge badge-danger">{{ $item['status'] }}</span>
                                                    @endif
                                                </td>
                                                <td class="text-center" width="20%">
                                                    <a href="{{ route('admin.games.edit', $item['hashId']) }}" class="btn btn-sm btn-primary mb-1 mt-1" title="Edit">
                                                        <i class="fa fa-edit text-white"></i>
                                                        Edit
                                                    </a>
                                                    <form action="{{ route('admin.games.delete', $item['hashId']) }}" method="POST" class="d-inline-block">
                                                        @csrf
                                                        <button type="submit" class="btn btn-sm btn-danger mb-1 mt-1" onclick="return confirm('Are you sure you want to delete this item?');" title="Delete">
                                                            <i class="fa fa-trash text-white"></i>
                                                            Delete
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        @endforeach

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
