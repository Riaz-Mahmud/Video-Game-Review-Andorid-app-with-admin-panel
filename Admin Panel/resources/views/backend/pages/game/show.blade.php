@extends('backend/layouts/master')

@section('title', 'Game Details')

@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">Game Details</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="{{ route('admin.dashboard') }}">Home</a></li>
                            <li class="breadcrumb-item active">Game Details</li>
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

                {{-- show game details --}}
                <div class="row">
                    <div class="col-12">
                        <table>
                            <tr>
                                <td width="20%">
                                    <h5>Image</h5>
                                </td>
                                <td width="80%">
                                    <img src="{{ \App\Helpers\ImageHelper::generateImage($data['row']['banner'], 'medium')}}" alt="Game Image" width="100px">
                                </td>
                            </tr>
                            <tr>
                                <td width="20%">
                                    <h5>Name</h5>
                                </td>
                                <td width="80%">
                                    <h5>{{ $data['row']['name'] }}</h5>
                                </td>
                            </tr>
                            <tr>
                                <td width="20%">
                                    <h5>Description</h5>
                                </td>
                                <td width="80%">
                                    <h5>{!! $data['row']['description'] !!}</h5>
                                </td>
                            </tr>

                            <tr>
                                <td width="20%">
                                    <h5>Status</h5>
                                </td>
                                <td width="80%">
                                    @if ($data['row']['status'] == 'Active')
                                        <span class="badge badge-success">{{ $data['row']['status'] }}</span>
                                    @else
                                        <span class="badge badge-danger">{{ $data['row']['status'] }}</span>
                                    @endif
                                </td>
                            </tr>
                            <tr>
                                <td width="20%">
                                    <h5>Created At</h5>
                                </td>
                                <td width="80%">
                                    <h5>{{ $data['row']['created_at'] }}</h5>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <hr>

                {{-- show game reviews --}}
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">Reviews</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="datatables-users" class="table table-bordered table-striped" style="width: 100%">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>User Name</th>
                                            <th>Comment</th>
                                            <th>Rating</th>
                                            <th>Location</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @forelse($data['row']['reviews'] as $key => $item)
                                            <tr>
                                                <td>{{ $key + 1 }}</td>
                                                <td>
                                                    {{ $item['user']['first_name'] }} {{ $item['user']['last_name'] }}
                                                </td>
                                                <td class="text-justify" width="20%">
                                                    {!! $item['comments'] !!}
                                                </td>
                                                <td class="text-center" width="10%">
                                                    {{ $item['rating'] }}<i class="fa fa-star text-warning"></i>
                                                </td>
                                                <td class="text-center" width="10%">
                                                    {{ $item['address'] }}
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
                                        @empty
                                            <tr>
                                                <td colspan="7" class="text-center">
                                                    <h5>No Data Found</h5>
                                                </td>
                                            </tr>
                                        @endforelse

                                    </tbody>
                                </table>
                                {{-- paggination --}}
                                <div class="d-flex justify-content-center">
                                    {{ $data['row']['reviews']->links() }}
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
