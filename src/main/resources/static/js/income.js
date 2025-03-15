
// Add new expense 
document.getElementById('addExpenseForm').addEventListener('submit', function() {
    const expenseCategory = document.getElementById('categoryName').value;
    const expenseAmount = document.getElementById('expenseAmount').value;
});


// Open the edit expense modal when edit button is clicked
document.querySelectorAll('.edit-btn').forEach(button => {
    button.addEventListener('click', function () {
        let expenseModal = new bootstrap.Modal(document.getElementById('editExpenseModal'));
        expenseModal.show();
    });
});

// Edit an expense
document.getElementById('editExpenseForm').addEventListener('submit', function() {
    const editExpenseCategory = document.getElementById('editCategoryName').value;
    const editExpenseAmount = document.getElementById('editExpenseAmount').value;
});

// Submit net income form
document.getElementById('netIncomeForm').addEventListener('submit', function() {
    const grossIncome = document.getElementById('grossIncome').value;
    const state = document.getElementById('state').value;
    const filingStatus = document.getElementById('filingStatus').value;
})



// States dropdown list code

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