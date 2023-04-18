<div class="alert alert-danger alert-dismissible fade show" role="alert">
    {{-- close after 5 sec --}}
    <script>
        setTimeout(function() {
            $('.alert').alert('close');
        }, 5000);
    </script>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="alert-heading">Error!</h4>
    <ul class="mb-0">
        @foreach ($errors->all() as $error)
            <li>{{ $error }}</li>
        @endforeach

        @if (session('error'))
            <li>{{ session('error') }}</li>
        @endif
    </ul>
</div>
