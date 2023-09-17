import React, { useEffect, useState } from "react";
import ProductItemLayout from "../components/ProductItemLayout";
import Footer from "../components/Footer";
import { useParams, useRouteMatch } from "react-router-dom";

function ProductPage() {
  const { id } = useParams();

  return (
    <>
      <ProductItemLayout productId={id} />
      <Footer />
    </>
  );
}

export default ProductPage;
