export function formatCurrenyVND(value) {
  return value.toLocaleString('it-IT', {
    style: 'currency', currency: 'VND'
  })
}
