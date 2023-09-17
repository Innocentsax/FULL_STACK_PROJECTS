import React from "react";
import SideBar from "../sidebar/SideBar";
import "./AdminLayout.css";
import { Grid, Container } from "semantic-ui-react";

const AdminLayout = ({ children }) => {
  const menu = [
    {
      icon: "",
      name: "Dashboard",
      path: "/admin/dashboard",
      exact: true,
    },
    {
      icon: "",
      name: "Orders",
      path: "/admin/orders",
      exact: true,
    },
    {
      icon: "",
      name: "Add Product",
      path: "/admin/products",
      exact: true,
    },
    {
      icon: "",
      name: "Product List",
      path: "/admin/productList",
      exact: true,
    },
  ];

  return (
    // <div className="dashboard-wrapper">
    <Container>
      <Grid>
        <Grid.Column mobile={16} tablet={16} largeScreen={5} computer={16}>
          <SideBar menuItems={menu} />
        </Grid.Column>
        <Grid.Column mobile={16} tablet={16} largeScreen={11} computer={16}>
          {children}
        </Grid.Column>
      </Grid>
    </Container>
  );
};

export default AdminLayout;
