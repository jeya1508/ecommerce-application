import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  token: "",
  id: "",
  role:""
};

export const tokenSlice = createSlice({
  name: "tokenLoader",
  initialState,
  reducers: {
    setSavedToken: (state, action) => {
      state.token = action.payload.token;
      state.id = action.payload.id;
      state.role=action.payload.role;
    },
  },
});

export const { setSavedToken } = tokenSlice.actions;

export default tokenSlice.reducer;
