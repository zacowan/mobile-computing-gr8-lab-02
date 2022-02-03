require 'nokogiri'
require 'open-uri'
require 'json'

domain = "http://stephenking.com"
res = Hash.new;
res["books"] = [];

page = Nokogiri::HTML(open(domain + "/library/written.html"))
count = 0;
page.css("td.worklist_title").each do |elem|
	puts domain + elem.children[0].attribute_nodes[0]
	ind = Nokogiri::HTML(open(domain + elem.children[0].attribute_nodes[0]))

	date = elem.parent.children[4].to_str()
	img = domain + ind.css("td.property_image_cell")[0].children[0].attribute_nodes[0]
	text = ind.css("td.property_text_cell")[0].to_str().encode("UTF-16BE", :invalid=>:replace, :replace=>"?").encode("UTF-8")

	# puts ind.css("td.property_text_cell")[0].to_str()
	res["books"].push({"title" => elem.to_str, "date" => date, "text" => text, "image" => img, "id" => count})
	count += 1
end

File.open("temp.json", "w") do |f|
  f.write(res.to_json)
end