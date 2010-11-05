function init() {
  externalizeLinks();toggleFacets();$("a.grouped_elements").fancybox();SyntaxHighlighter.all();
}
function externalizeLinks() {
  $('a[rel="external"]').addClass("external");
  $('a[rel="external"]').hover(function() {
    $(this).attr("title","Opens in new window: "+$(this).text());
  });
  $('a[rel="external"]').click( function() {
      window.open( $(this).attr('href') ); return false;
  });
}
function toggleFacets() {
  $('a.toggle').click(function(){
    $(this).parent().parent().find('.more').toggle();
    $(this).parent().parent().find('.less').toggle();
  });
} 
$('#commentform').submit(function() {
  var valid = true;
  if ($('#person').val()==='') {
    $('#person').parent().addClass('error');
    valid = false;
  } else {
    $('#person').parent().removeClass('error');
  }
  if ($('#comment').val()==='') {
    $('#comment').parent().addClass('error');
    valid = false;
  } else {
    $('#comment').parent().removeClass('error');
  }
  return valid;
});