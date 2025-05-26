function toggleEditForm(id) {
  const form = document.getElementById('edit-form-' + id);
  const editButton = document.getElementById('edit-button-' + id);
  const deleteForm = document.getElementById('delete-form-' + id);

  if (form.style.display === 'none') {
    form.style.display = 'block';
    editButton.style.display = 'none';
    deleteForm.style.display = 'none';
  } else {
    form.style.display = 'none';
    editButton.style.display = 'inline-block';
    deleteForm.style.display = 'inline-block';
  }
}
