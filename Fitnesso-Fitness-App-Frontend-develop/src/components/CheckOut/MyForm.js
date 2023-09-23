import React, {useState} from 'react';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import Input from '@mui/material/Input';
import FilledInput from '@mui/material/FilledInput';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import TextField from '@mui/material/TextField';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';


const MyForm = () => {
    const [values, setValues] = useState({
        amount: '',
        password: '',
        weight: '',
        weightRange: '',
        showPassword: false,
      });
    
      const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
      };
    
      const handleClickShowPassword = () => {
        setValues({
          ...values,
          showPassword: !values.showPassword,
        });
      };
    
      const handleMouseDownPassword = (event) => {
        event.preventDefault();
      };
      const handleSubmit = evt => {
        evt.preventDefault();
        console.log(values);
    }
    
      return (
        <form onSubmit={handleSubmit}>
        <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
          <div>
            <FormControl sx={{ m: 1, width: '25ch' }} variant="filled">
              <InputLabel htmlFor="filled-adornment-password">Password</InputLabel>
              <FilledInput
                id="filled-adornment-password"
                type={values.showPassword ? 'text' : 'password'}
                value={values.password}
                onChange={handleChange('password')}
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      onMouseDown={handleMouseDownPassword}
                      edge="end"
                    >
                      {values.showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
              />
            </FormControl>
            <FormControl fullWidth sx={{ m: 1 }} variant="filled">
              <InputLabel htmlFor="my-input">Amount</InputLabel>
              <FilledInput
                id="filled-adornment-amount"
                aria-describedby="my-helper-text"
                value={values.amount}
                onChange={handleChange('amount')}
                // startAdornment={<InputAdornment position="start">$</InputAdornment>}
              />
            </FormControl>
          </div>
          
        </Box>
        <Button
            type="submit"
            variant="contained"
            color="primary"
          >
            Subscribe
          </Button>
        </form>
      );
}

export default MyForm;