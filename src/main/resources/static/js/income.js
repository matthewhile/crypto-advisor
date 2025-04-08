document.addEventListener("DOMContentLoaded", function () {
    loadExpenses();
});

// Load user's expenses
function loadExpenses() {
    fetch("http://localhost:8080/api/expenses")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("expenseTableBody");
            tableBody.innerHTML = "";

            data.forEach(expense => {
                addExpenseRow(expense, tableBody, false);
            });
            
            updateTotalExpense();
            attachEventListeners();
        })
        .catch(error => console.error("Error fetching expenses:", error));
}

// Add new expense
document.getElementById('addExpenseForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const category = document.getElementById('categoryName').value;
    const amount = document.getElementById('expenseAmount').value;

    const data = { category, amount };

    fetch('/api/expenses/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(expense => {
        const tableBody = document.getElementById("expenseTableBody");
        addExpenseRow(expense, tableBody, true); 
        updateTotalExpense();
        document.getElementById('addExpenseForm').reset(); 
        attachEventListeners(); 
    })
    .catch(error => console.error('Error:', error));
});

// Add a new expense row to the table
function addExpenseRow(expense, tableBody, isNew = true) {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${expense.category}</td>
        <td class="expense-amount">$${parseFloat(expense.amount).toFixed(2)}</td>
        <td class="edit-delete-expense">
            <button type="button" class="btn edit-btn" data-id="${expense.id}"
                data-category="${expense.category}" data-amount="${expense.amount}">
                <i class="fa-solid fa-pencil"></i>
            </button>
            <button class="btn delete-btn" data-id="${expense.id}">
                <i class="fa-regular fa-trash-can"></i>
            </button>
        </td>   
    `;

    if (isNew) {
        tableBody.prepend(row);
    } else {
        tableBody.appendChild(row);
    }
}

// Edit and delete button click event listeners
function attachEventListeners() {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", deleteExpense);
    });

    document.querySelectorAll(".edit-btn").forEach(button => {
        button.addEventListener("click", openEditModal);
    });
}

// Delete an expense 
function deleteExpense() {
    const expenseId = this.getAttribute("data-id");  

    fetch(`/api/expenses/delete/${expenseId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            this.closest('tr').remove();
            updateTotalExpense();
        } 
    })
    .catch(error => {
        console.error("Error:", error);
        alert("There was an error deleting the expense");
    });
};

// Open and set edit expense modal
function openEditModal() {
    const expenseId = this.getAttribute("data-id");
    const category = this.getAttribute("data-category");
    const amount = this.getAttribute("data-amount");

    document.getElementById("editCategoryName").value = category;
    document.getElementById("editExpenseAmount").value = amount;
    document.getElementById("editExpenseForm").setAttribute("data-id", expenseId);

    // Open the modal
    let expenseModal = new bootstrap.Modal(document.getElementById("editExpenseModal"));
    expenseModal.show();
}

// Save edit for an expense
document.getElementById('editExpenseForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const expenseId = this.getAttribute("data-id");
    const category = document.getElementById('editCategoryName').value;
    const amount = parseFloat(document.getElementById('editExpenseAmount').value).toFixed(2);
    
    const data = { category, amount };

    fetch(`/api/expenses/update/${expenseId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(updatedExpense => {
        // Find the row in the table and update it
        const row = document.querySelector(`button[data-id='${expenseId}']`).closest("tr");
        if (row) {
            row.children[0].textContent = updatedExpense.category; 
            row.children[1].textContent = `$${parseFloat(updatedExpense.amount).toFixed(2)}`; 
            updateTotalExpense();
        }

        // Close the modal
        let expenseModal = bootstrap.Modal.getInstance(document.getElementById("editExpenseModal"));
        expenseModal.hide();
    })
    .catch(error => console.error('Error updating expense:', error));
});

function updateTotalExpense() {
    let total = 0;
    document.querySelectorAll('.expense-amount').forEach(cell => {
        const amount = parseFloat(cell.textContent.replace('$', ''));
        if (!isNaN(amount)) total += amount;
    });
    document.getElementById('totalExpense').textContent = `$${total.toFixed(2)}`;
}



// Submit net income form
document.getElementById('netIncomeForm').addEventListener('submit', function() {
    const grossIncome = document.getElementById('grossIncome').value;
    const state = document.getElementById('state').value;
    const filingStatus = document.getElementById('filingStatus').value;
})

document.addEventListener("DOMContentLoaded", function() {
    loadStatesDropdown();
    loadFilingStatusDropdown();
});

// States dropdown list code

function loadStatesDropdown() {
    const states = [
        { code: "AL", name: "Alabama" },
        { code: "AK", name: "Alaska" },
        { code: "AZ", name: "Arizona" },
        { code: "AR", name: "Arkansas" },
        { code: "CA", name: "California" },
        { code: "CO", name: "Colorado" },
        { code: "CT", name: "Connecticut" },
        { code: "DE", name: "Delaware" },
        { code: "FL", name: "Florida" },
        { code: "GA", name: "Georgia" },
        { code: "HI", name: "Hawaii" },
        { code: "ID", name: "Idaho" },
        { code: "IL", name: "Illinois" },
        { code: "IN", name: "Indiana" },
        { code: "IA", name: "Iowa" },
        { code: "KS", name: "Kansas" },
        { code: "KY", name: "Kentucky" },
        { code: "LA", name: "Louisiana" },
        { code: "ME", name: "Maine" },
        { code: "MD", name: "Maryland" },
        { code: "MA", name: "Massachusetts" },
        { code: "MI", name: "Michigan" },
        { code: "MN", name: "Minnesota" },
        { code: "MS", name: "Mississippi" },
        { code: "MO", name: "Missouri" },
        { code: "MT", name: "Montana" },
        { code: "NE", name: "Nebraska" },
        { code: "NV", name: "Nevada" },
        { code: "NH", name: "New Hampshire" },
        { code: "NJ", name: "New Jersey" },
        { code: "NM", name: "New Mexico" },
        { code: "NY", name: "New York" },
        { code: "NC", name: "North Carolina" },
        { code: "ND", name: "North Dakota" },
        { code: "OH", name: "Ohio" },
        { code: "OK", name: "Oklahoma" },
        { code: "OR", name: "Oregon" },
        { code: "PA", name: "Pennsylvania" },
        { code: "RI", name: "Rhode Island" },
        { code: "SC", name: "South Carolina" },
        { code: "SD", name: "South Dakota" },
        { code: "TN", name: "Tennessee" },
        { code: "TX", name: "Texas" },
        { code: "UT", name: "Utah" },
        { code: "VT", name: "Vermont" },
        { code: "VA", name: "Virginia" },
        { code: "WA", name: "Washington" },
        { code: "WV", name: "West Virginia" },
        { code: "WI", name: "Wisconsin" },
        { code: "WY", name: "Wyoming" }
    ];
    
    const stateDropdown = document.getElementById("state");
    
    states.forEach(state => {
        let option = document.createElement("option");
        option.value = state.code;
        option.textContent = state.name;
        stateDropdown.appendChild(option);
    });
};

// Load filing status dropdown 

// function loadFilingStatusDropdown() {

//     const statuses = [
//         {value: 1, name: "Single"}, 
//         {value: 2, name: "Married Filing Jointly"}, 
//         {value: 3, name: "Married Filing Separately"}, 
//         {value: 4, name: "Head of Household"}, 
//         {value: 5, name: "Qualifying Widow(er)"}, 
//     ];

//     const statusDropdown = document.getElementById("filingStatus");

//     statuses.forEach(status => {
//         let option = document.createElement("option");
//         option.value = status.value;
//         option.textContent = status.name;
//         statusDropdown.appendChild(option);
//     });
//};
