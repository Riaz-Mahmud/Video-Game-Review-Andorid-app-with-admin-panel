<div class="alert alert-success alert-dismissible fade show" role="alert" style="background-color: #ffffff; margin-start: 15px; margin-end: 15px;">
    <script>
        setTimeout(function() {
            $('.alert').alert('close');
        }, 3000);
    </script>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="alert-heading">Success!</h4>
    <p class="mb-0 text-success">
        {{ session('success') }}
    </p>

</div>

