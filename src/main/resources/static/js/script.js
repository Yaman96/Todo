document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/tasks')
        .then(response => response.json())
        .then(tasks => {
            // Get the table body element
            const tableBody = document.getElementById('taskTableBody');

            // Loop through the tasks and create table rows
            tasks.forEach((task, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                            <th scope="row">${index + 1}</th>
                            <td>${task.task}</td>
                            <td class="text-center">${formatDateTime(task.deadline)}</td>
                        `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching tasks:', error));

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
});
