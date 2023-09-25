import * as React from "react";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import "./SelectDropdown.css";

export default function SelectDropdown(props) {
  return (
    <div>
      <FormControl sx={{ width: "100%" }}>
        <Select
          value={props.value}
          onChange={props.onChange}
          displayEmpty={true}
          inputProps={{ "aria-label": "Without label" }}
          name={props.name}
          id={props.id}>
          <MenuItem value=''>
            <em>{props.name}</em>
          </MenuItem>
          {props.dropValues.length >= 1 &&
            props.dropValues.map((dropValue) => {
              return (
                <MenuItem value={dropValue.code} key={dropValue.id}>
                  {dropValue.name}
                </MenuItem>
              );
            })}
        </Select>
      </FormControl>
    </div>
  );
}
