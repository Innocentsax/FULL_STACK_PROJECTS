import React from "react";
import "./NoBudgetCreated.css";
import { Link } from "react-router-dom";

function NoBudgetCreated() {
  return (
    <div class="no-budget-created-decapay-WRF">
      <div class="frame-8755-Ec9">
        <div class="frame-8753-MRs">
          <div class="frame-8752-sf7">
            <img class="group-yy3" src="/assets/group-tL1.png" />
          </div>
          <div class="frame-8678-u61">
            <div class="no-budget-yet-4Uh">No Budget yet</div>
            <div class="youve-not-created-any-budget-list-yet-click-on-the-sign-icon-below-to-create-a-budget-NkH">
              You’ve not created any budget list yet.
              <br />
              Click on the + sign icon below to
              <br />
              create a budget.
            </div>
          </div>
        </div>
        <div class="frame-8754-dw7">
          <img class="plus-N81" src="./assets/plus.png" />

          <Link to="/decapay/create-budget" class="create-budget-75b">
            Create Budget
          </Link>

        </div>
      </div>
    </div>
  );
}
export default NoBudgetCreated;
