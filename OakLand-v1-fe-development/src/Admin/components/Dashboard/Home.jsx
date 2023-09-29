import "./home.scss";
import Widget from "./Widget";
import Featured from "./Featured";
import OrderTableView from "../Order/OrdersTableView";
import PersonTableView from "../Person/PersonTableView";
import TableView from "../Product/TableView";
import PickupTableView from "../PickupCenter/PickupTableView";

const Home = () => {
  return (
      <div className="homeContainer">
        <div className="widgets">
          <Widget type="user" />
          <Widget type="order" />
          <Widget type="earning" />
          <Widget type="balance" />
        </div>
        <div className="charts">
          <div className="featured" style={{height: "40vh", width: "100%", overflow: "hidden", marginBottom: "50px", }}>
              <PickupTableView tableTitle="PICKUP CENTERS" />
          </div>
          {/* <Chart title="Last 6 Months (Revenue)" aspect={2 / 1} /> */}
          <div className="featured" style={{height: "40vh", width: "100%", overflow: "hidden", marginBottom: "50px", }}>
            <TableView tableTitle={ "PRODUCTS" }/>
          </div>
        </div>
        <div className="listContainer">
          <div className="listTitle">Latest Orders</div>
          <OrderTableView tableTitle={"ORDERS"}/> 
          {/* <PickupTableView tableTitle="PICKUP CENTERS" /> */}

        </div>
    </div>
  );
};

export default Home;
