function toggleEditForm(commentId) {
  var viewSpan = document.getElementById('view-' + commentId);
  var editForm = document.getElementById('edit-' + commentId);
  var editButton = document.getElementById('edit-button-' + commentId);

  if (editForm.style.display === 'none') {
    viewSpan.style.display = 'none';
    editForm.style.display = 'inline';
    editButton.textContent = '취소';
  } else {
    viewSpan.style.display = 'inline';
    editForm.style.display = 'none';
    editButton.textContent = '수정';
  }
}
