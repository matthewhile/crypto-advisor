// document.addEventListener('DOMContentLoaded', () => {
//   const container = document.getElementById('statusContainer');

//   // === Form: Net Income & Expenses ===
//   const grossIncome = localStorage.getItem('grossIncome');
//   const selectedState = localStorage.getItem('state');
//   const filingStatus = localStorage.getItem('filingStatus');
//   const expenses = JSON.parse(localStorage.getItem('expenses') || '[]');

//   const incomeComplete = grossIncome && selectedState && filingStatus;
//   const expensesComplete = expenses.length > 0;

//   // === Form: Preferences ===
//   const investmentAmount = localStorage.getItem('investmentAmount');
//   const timeFrame = localStorage.getItem('timeFrame');
//   const frequency = localStorage.getItem('frequency');
//   const riskTolerance = localStorage.getItem('riskTolerance');

//   const preferencesComplete = investmentAmount && timeFrame && frequency && riskTolerance;

//   // === Render Section Function ===
//   function renderSection(title, isComplete, detailsHTML) {
//     return `
//       <div class="status-box">
//         <h4>${title}</h4>
//         <p>Status: <span class="${isComplete ? 'complete' : 'incomplete'}">
//           ${isComplete ? '✅ Complete' : '❌ Incomplete'}</span></p>
//         ${detailsHTML}
//       </div>
//     `;
//   }

//   // === Income Section ===
//   const incomeDetails = `
//     <ul>
//       <li><strong>Annual Income:</strong> ${grossIncome || 'Not set'}</li>
//       <li><strong>State:</strong> ${selectedState || 'Not set'}</li>
//       <li><strong>Filing Status:</strong> ${filingStatus || 'Not set'}</li>
//     </ul>`;
//   container.innerHTML += renderSection("Net Income Form", incomeComplete, incomeDetails);

//   // === Expenses Section ===
//   const expenseDetails = expenses.length
//     ? `<ul>${expenses.map(e => `<li>${e.category}: $${e.amount}</li>`).join('')}</ul>`
//     : `<p>No expenses recorded.</p>`;
//   container.innerHTML += renderSection("Expense Entries", expensesComplete, expenseDetails);

//   // === Preferences Section ===
//   const preferencesDetails = `
//     <ul>
//       <li><strong>Investment Amount:</strong> ${investmentAmount || 'Not set'}</li>
//       <li><strong>Time Frame:</strong> ${timeFrame || 'Not set'}</li>
//       <li><strong>Frequency:</strong> ${frequency || 'Not set'}</li>
//       <li><strong>Risk Tolerance:</strong> ${riskTolerance || 'Not set'}</li>
//     </ul>`;
//   container.innerHTML += renderSection("Investment Preferences", preferencesComplete, preferencesDetails);
// });