// 수정 폼 보여 주기
function showEditForm(commentId) {
  // 모든 기존의 수정 폼을 숨김
  hideEditForm();

  // 댓글 행 찾기
  const commentRow = document.querySelector(`#edit-btn-${commentId}`).closest('tr');
  if (!commentRow) {
    return;
  }

  // 수정 폼 행 찾기
  const editFormRow = document.getElementById(`edit-form-${commentId}`);
  if (!editFormRow) {
    return;
  }

  // 수정 폼을 댓글 행 다음에 추가
  commentRow.insertAdjacentElement('afterend', editFormRow);
  editFormRow.style.display = 'table-row';
}

// 수정 폼 숨기기
function hideEditForm() {
  // 모든 수정 폼 행 숨기기
  document.querySelectorAll('[id^="edit-form-"]').forEach(form => {
    form.style.display = 'none';
  });
}