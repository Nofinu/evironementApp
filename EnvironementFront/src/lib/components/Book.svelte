<script> 
   export let bookTitle; 
   export let bookPrice; 
   export let bookDescription; 
</script>

<style> 
   .book-card { 
      background-color: #f8f8f8; 
      border-radius: 8px; 
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      padding: 1rem; 
      margin-bottom: 1rem; 
      transition: box-shadow 0.3s ease; 
   } 
   .book-card:hover { 
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); 
   } 
   h2 { 
      font-size: 1.2rem; 
      color: #333; 
      margin: 0 0 0.5rem 0; 
   } 
   p { 
      margin: 0.5rem 0; 
      color: #666; 
   } 
   button { 
      background-color: #4a4a4a; 
      color: white; 
      border: none; 
      padding: 0.5rem 1rem; 
      border-radius: 4px; 
      cursor: pointer; 
      transition: background-color 0.3s ease; 
   } 
   button:hover { 
      background-color: #333; 
   } 
</style>

<div class="book-card"> 
   <h2>{bookTitle}</h2> 
   <p><strong>Price:</strong> ${bookPrice}</p>
   <p>{bookDescription}</p> 
   <button>Add to Cart</button> 
</div>