document.addEventListener('DOMContentLoaded', () => {
  loadTables();
  loadGraphs();
});

moment.relativeTimeThreshold('ss', 4);
Chart.defaults.global.defaultFontColor = '#212529';
Chart.defaults.global.defaultColor = 'white';

function loadGraphs() {
  const containers = document.querySelectorAll('[data-format="graph"]');
  containers.forEach(container => {
    loadGraph(container);
  });

  setTimeout(loadGraphs, 10000);
}

function loadGraph(container) {
  const url = container.attributes.getNamedItem('data-url').value;
  const urlKey = container.attributes.getNamedItem('data-url-key').value;
  const canvas = container.querySelector('canvas');
  const lastUpdate = container.querySelector('[data-last-update]');
  let chart = container['chart'];

  if (urlKey === null || urlKey.length === 0) {
    return;
  }

  fetch(url + urlKey)
    .then(value => value.json())
    .then(datasets => {
      const ctx = canvas.getContext('2d');
      if (!chart) {
        chart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: datasets.length > 0 ? datasets[0].data.map(d => d.t) : [],
            datasets
          },
          options: {
            scales: {
              xAxes: [
                {
                  type: 'time',
                  time: {
                    unit: 'minute'
                  },
                }],
            },
          }
        });
        container['chart'] = chart;
      } else {
        chart.data = {
          labels: datasets.length > 0 ? datasets[0].data.map(d => d.t) : [],
          datasets
        };
        chart.update(0);
      }

      lastUpdate.setAttribute('data-livestamp', new Date().toISOString());
    });
}

function loadTables() {
  const containers = document.querySelectorAll('[data-format="table"]');
  containers.forEach(container => {
    const url = container.attributes.getNamedItem('data-url').value;
    const table = container.querySelector('[data-table]');
    const lastUpdate = container.querySelector('[data-last-update]');

    fetch(url)
      .then(value => value.json())
      .then(value => {
        value.forEach(stat => {
          let row = table.querySelector(`[data-key="${stat.key}"]`);

          if (row == null) {
            row = createRow(stat.key);

            table.appendChild(row);
          }

          const keyElement = row.querySelector('[data-key-col]');
          keyElement.innerText = stat.key;

          const dataElement = row.querySelector('[data-data-col]');
          dataElement.innerText = getValue(stat);
        });

        lastUpdate.setAttribute('data-livestamp', new Date().toISOString());
      });
  });

  setTimeout(loadTables, 1000);
}

function createRow(key) {
  const row = document.createElement('tr');
  row.setAttribute('data-key', key);
  row.onclick = ev => selectGraph(key);

  const keyElement = document.createElement('td');
  keyElement.setAttribute('data-key-col', null);
  row.appendChild(keyElement);

  const dataElement = document.createElement('td');
  dataElement.setAttribute('data-data-col', null);
  row.appendChild(dataElement);

  return row;
}

function selectGraph(key) {
  const container = document.querySelector('#main-graph');
  container.setAttribute('data-url-key', key);
  loadGraph(container);
}

function getValue(data) {
  switch (data.unit) {
    case 'bytes':
      return filesize(data.value, {standard: 'iec'});
    default:
      return Math.round(data.value * 100)/100;
  }
}
