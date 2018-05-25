document.addEventListener('DOMContentLoaded', () => {
  loadTables();
});

moment.relativeTimeThreshold('ss', 4);

function loadTables() {
  const containers = document.querySelectorAll('[data-format="table"]');
  containers.forEach(container => {
    const url = container.attributes.getNamedItem('data-url').value;
    const table = container.querySelector('[data-table]');
    const lastUpdate = container.querySelector('[data-last-update]');
    fetch(url)
      .then(value => value.json())
      .then(value => {
        for (const key in value.data) {
          if (!value.data.hasOwnProperty(key)) {
            continue;
          }

          let row = table.querySelector(`[data-key="${key}"]`);

          if (row == null) {
            row = createRow(key);

            table.appendChild(row);
          }

          const keyElement = row.querySelector('[data-key-col]');
          keyElement.innerText = key;

          const dataElement = row.querySelector('[data-data-col]');
          dataElement.innerText = getValue(value.data[key]);
        }

        lastUpdate.setAttribute('data-livestamp', value.time);
      });
  });

  setTimeout(loadTables, 1000);
}

function createRow(key) {
  const row = document.createElement('tr');
  row.setAttribute('data-key', key);

  const keyElement = document.createElement('td');
  keyElement.setAttribute('data-key-col', null);
  row.appendChild(keyElement);

  const dataElement = document.createElement('td');
  dataElement.setAttribute('data-data-col', null);
  row.appendChild(dataElement);

  return row;
}

function getValue(data) {
  switch (data.unit) {
    case 'bytes':
      return filesize(data.value, {standard: 'iec'});
    default:
      return Math.round(data.value * 100)/100;
  }
}
