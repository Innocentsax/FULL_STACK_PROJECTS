export const clientId =
  "887116854395-0mo6aorlnmr7bci8apjvjl7p5tnqin8b.apps.googleusercontent.com";

const currencyFormat = {
  style: "currency",
  currency: "NGN",
  minimumFractionDigits: 2,
};
export const currency = new Intl.NumberFormat("en-NG", currencyFormat);

export const screenSize = window.innerWidth;
export const pageLimit = 10;

export const spinnerSize = "25px";
export const spinnerColor = "white";
export const spinnerNumberOfRotation = 5;
