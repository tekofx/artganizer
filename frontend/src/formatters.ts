function padTo2Digits(num: number) {
  return num.toString().padStart(2, "0");
}

export function formatDate(date: Date | undefined) {
  if (date == undefined) {
    return "";
  }
  date = new Date(date);
  return (
    [
      padTo2Digits(date.getDate()),
      padTo2Digits(date.getMonth() + 1),
      date.getFullYear(),
    ].join("/") +
    " " +
    [
      padTo2Digits(date.getHours()),
      padTo2Digits(date.getMinutes()),
      padTo2Digits(date.getSeconds()),
    ].join(":")
  );
}

export function convertBytes(bytes: number | undefined) {
  if (bytes == undefined) {
    return "unknown";
  }
  if (bytes > 1000000) {
    var mb = (bytes / 1000000).toFixed(2);
    return `${mb} MB`;
  } else {
    var kb = (bytes / 1000).toFixed(2);
    return `${kb} KB`;
  }
}
