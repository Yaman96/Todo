const pageSize = 5; // Set your desired page size
let currentPage = 0;
let totalPages = 0;
document.getElementById('submitBtn').addEventListener('click', function () {
    // Clear previous error messages
    document.getElementById('taskError').textContent = '';
    document.getElementById('deadlineError').textContent = '';

    // Get form inputs
    const taskInput = document.getElementById('task');
    const deadlineInput = document.getElementById('deadline');

    // Validate inputs
    if (taskInput.value.trim() === '') {
        document.getElementById('taskError').textContent = 'Please enter your task';
        return;
    }
    if (!deadlineInput.value) {
        document.getElementById('deadlineError').textContent = 'Please select a deadline';
        return;
    }

    // If all validations pass, create an object with the form data
    const formData = {
        task: taskInput.value,
        deadline: deadlineInput.value
    };

    // Send a POST request to /api/tasks
    fetch('/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add task');
            }
            // If the response is okay, reload the page
            window.location.reload();
        })
        .catch(error => {
            // Handle errors
            console.error('Error adding task:', error);
        });
});
document.addEventListener('DOMContentLoaded', function () {
    fetchTasks(currentPage, pageSize);
});

function openEditModal(taskId) {
    // Set the task ID in the hidden input field
    document.getElementById('editedTaskId').value = taskId;

    // Fetch existing task data
    fetch(`/api/tasks/${taskId}`)
        .then(response => response.json())
        .then(task => {
            // Populate the form fields with existing task data
            document.getElementById('editedTask').value = task.task;
            document.getElementById('editedDeadline').value = task.deadline;
            // Add additional field population logic as needed
        })
        .catch(error => console.error('Error fetching task:', error));

    // Logic to open the edit modal for the specified task ID
    // You can use Bootstrap's modal methods to show the modal
    const editTaskModal = new bootstrap.Modal(document.getElementById('editTaskModal'));
    editTaskModal.show();
}


function deleteTask(taskId) {
    // Logic to delete the task with the specified task ID
    fetch(`/api/tasks/${taskId}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete task');
            }
            // Handle success, if needed
            console.log('Task deleted successfully');
            window.location.reload();
            // Optionally, you can remove the row from the table on the client side
            // row.remove();
        })
        .catch(error => {
            // Handle errors
            console.error('Error deleting task:', error);
        });
}
function saveEditedTask() {
    // Get data from the modal inputs
    const editedTaskId = document.getElementById('editedTaskId').value;
    const editedTask = document.getElementById('editedTask').value;
    const editedDeadline = document.getElementById('editedDeadline').value;

    // Prepare the data for the PUT request
    const formData = {
        task: editedTask,
        deadline: editedDeadline,
        // Add other fields as needed
    };

    // Send a PUT request to /api/tasks/id
    fetch(`/api/tasks/${editedTaskId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to save edited task');
            }
            // If the response is okay, refresh the page
            window.location.reload();
        })
        .catch(error => {
            // Handle errors
            console.error('Error saving edited task:', error);
        });
}

function fetchTasks(page, size) {
    fetch(`/api/tasks?page=${page}&size=${size}`)
        .then(response => response.json())
        .then(tasks => {
            const tableBody = document.getElementById('taskTableBody');
            tableBody.innerHTML = ''; // Clear existing rows

            tasks.content.forEach((task, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                        <th scope="row">${index + 1}</th>
                        <td>${task.task}</td>
                        <td class="text-center">${formatDateTime(task.deadline)}</td>
                        <td class="text-center">
                        <button type="button" class="btn" onclick="openEditModal(${task.id})"><span class="bi bi-pen"></span></button>
                        <button type="button" class="btn" onclick="deleteTask(${task.id})"><span class="bi bi-trash"></span></button>
                        </td>
                    `;
                tableBody.appendChild(row);
            });

            totalPages = tasks.totalPages;

            // Add pagination controls
            const paginationControls = document.getElementById('paginationControls');
            paginationControls.innerHTML = createPaginationControls();
        })
        .catch(error => console.error('Error fetching tasks:', error));
}
function createPaginationControls() {
    const paginationHtml = `
        <nav aria-label="Page navigation" class="custom-pagination">
    <ul class="pagination">
        <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="updatePage(${currentPage - 1})" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li class="page-item">
            <span class="page-link">${currentPage + 1}</span>
        </li>
        <li class="page-item ${currentPage + 1 >= totalPages ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="updatePage(${currentPage + 1})" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
    `;

    return paginationHtml;
}

function updatePage(newPage) {
    if (newPage >= 0) {
        currentPage = newPage;
        fetchTasks(currentPage,pageSize);
    }
}
function formatDateTime(dateTimeString) {
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    const formattedDateTime = new Date(dateTimeString).toLocaleString(undefined, options);
    return formattedDateTime.replace(',', ''); // Remove the comma from the default date format
}

function updateClock() {
    var now = new Date();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var day = now.getDate();
    var month = now.toLocaleString('default', { month: 'short' });
    var year = now.getFullYear();

    // Форматирование времени
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;

    var timeString = hours + '<span id="colon">:</span>' + minutes;
    var dateString = day + ' ' + month + ' ' + year;

    // Обновление содержимого элементов с идентификаторами "clock" и "date"
    document.getElementById('clock').innerHTML = timeString;
    document.getElementById('date').textContent = dateString;
}

// Вызывайте функцию updateClock каждую секунду
setInterval(updateClock, 1000);

// Вызовите updateClock сразу после загрузки страницы, чтобы избежать задержки
updateClock();