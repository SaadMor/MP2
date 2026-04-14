chrome.runtime.onInstalled.addListener(() => {
  chrome.contextMenus.create({
    id: 'translate-to-darija',
    title: 'Translate to Darija',
    contexts: ['selection']
  });
});

chrome.contextMenus.onClicked.addListener(async (info, tab) => {
  if (info.menuItemId !== 'translate-to-darija' || !tab?.id) return;

  await chrome.storage.local.set({ selectedText: info.selectionText || '' });
  await chrome.sidePanel.open({ tabId: tab.id });
});
