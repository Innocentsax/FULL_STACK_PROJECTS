import React, { useState } from "react";
import "./CustomerInfo.css";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Button from "@mui/material/Button";
import Input from "@mui/material/Input";
import FilledInput from "@mui/material/FilledInput";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import InputAdornment from "@mui/material/InputAdornment";
import FormHelperText from "@mui/material/FormHelperText";
import FormControl from "@mui/material/FormControl";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

function CustomerInfo({customerInfo, setCustomerInfo}) {

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCustomerInfo({ [name]: value });
  }
  return (
    <div>
      {/* <Container maxWidth="sm" >
          <Box sx={{ borderRadius: 16 }}>
          <div className="heading">Customer Info</div>
        <FormControl fullWidth sx={{ m: 1 }} variant="filled">
          <InputLabel htmlFor="my-input">Email</InputLabel>
          <FilledInput
            id="filled-adornment-amount"
            aria-describedby="my-helper-text"
            value={values.amount}
            onChange={handleChange("amount")}
          />
        </FormControl>
          </Box>
      </Container> */}
      <div className="CustomerInfo-container">
        <div className="CustomerInfo-row CustomerInfo-split CustomerInfo-title-content">
          <div className="CustomerInfo-design CustomerInfo-heading">Customer Info</div>
          <div className="CustomerInfo-design">*Required</div>
        </div>
        <div className="CustomerInfo-info-input CustomerInfo-title-content CustomerInfo-input-container">
          <label>Email* </label>
          <input type="text" name="email" value={customerInfo.email || ''}  onChange={handleInputChange} required/>
        </div>
      </div>
    </div>
  );
}

export default CustomerInfo;
