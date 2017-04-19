// Validation errors messages for Parsley
// Load this after Parsley

Parsley.addMessages('en', {
  dateiso: "This value should be a valid date (YYYY-MM-DD).",
  alphanumber:"This value should confirm to rules: alphabet ＋ number and the length is in 6~10",
  appidnumber:"This value should use the format:alphabet ＋ number  + .",
  appnamenumber:"This value can’t contain special characters like #",
  checkstartdate:"This value means that the start of access time should be earlier than  the end",
  checkdate:"This value means that the end of access time should be earlier than the start ",
  sourcepwdrequired:"This value should be old password",
  cellphone:"This value should be correct phone number",
  alphanumberrequire:"This value should confirm to rules: alphabet ＋ number and the length is in 6~10",
  startdatestr:"This value is not the correct format of time",
  enddatestr:"This value is not the correct format of time",
  comparedate:"This value means that the second period of time should not be longer than the first period of time"
});